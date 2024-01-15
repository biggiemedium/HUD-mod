package dev.px.hud.Util.API.Shader;

import dev.px.hud.Util.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

import java.io.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class Shaderutil implements Wrapper {

    private int programID;
    private final String roundedRectGradient = "#version 120\n" +
            "\n" +
            "uniform vec2 location, rectSize;\n" +
            "uniform vec4 color1, color2, color3, color4;\n" +
            "uniform float radius;\n" +
            "\n" +
            "#define NOISE .5/255.0\n" +
            "\n" +
            "float roundSDF(vec2 p, vec2 b, float r) {\n" +
            "    return length(max(abs(p) - b , 0.0)) - r;\n" +
            "}\n" +
            "\n" +
            "vec3 createGradient(vec2 coords, vec3 color1, vec3 color2, vec3 color3, vec3 color4){\n" +
            "    vec3 color = mix(mix(color1.rgb, color2.rgb, coords.y), mix(color3.rgb, color4.rgb, coords.y), coords.x);\n" +
            "    //Dithering the color\n" +
            "    // from https://shader-tutorial.dev/advanced/color-banding-dithering/\n" +
            "    color += mix(NOISE, -NOISE, fract(sin(dot(coords.xy, vec2(12.9898, 78.233))) * 43758.5453));\n" +
            "    return color;\n" +
            "}\n" +
            "\n" +
            "void main() {\n" +
            "    vec2 st = gl_TexCoord[0].st;\n" +
            "    vec2 halfSize = rectSize * .5;\n" +
            "    \n" +
            "    float smoothedAlpha =  (1.0-smoothstep(0.0, 2., roundSDF(halfSize - (gl_TexCoord[0].st * rectSize), halfSize - radius - 1., radius))) * color1.a;\n" +
            "    gl_FragColor = vec4(createGradient(st, color1.rgb, color2.rgb, color3.rgb, color4.rgb), smoothedAlpha);\n" +
            "}";
    private final String roundedRect =
            "#version 120\n" +
                    "\n" +
                    "uniform vec2 location, rectSize;\n" +
                    "uniform vec4 color;\n" +
                    "uniform float radius;\n" +
                    "uniform bool blur;\n" +
                    "\n" +
                    "float roundSDF(vec2 p, vec2 b, float r) {\n" +
                    "    return length(max(abs(p) - b, 0.0)) - r;\n" +
                    "}\n" +
                    "\n" +
                    "\n" +
                    "void main() {\n" +
                    "    vec2 rectHalf = rectSize * .5;\n" +
                    "    // Smooth the result (free antialiasing).\n" +
                    "    float smoothedAlpha =  (1.0-smoothstep(0.0, 1.0, roundSDF(rectHalf - (gl_TexCoord[0].st * rectSize)," +
                    " rectHalf - radius - 1., radius))) * color.a;\n" +
                    "    gl_FragColor = vec4(color.rgb, smoothedAlpha);// mix(quadColor, shadowColor, 0.0);\n" +
                    "\n" +
                    "}";
    private final String blurShader =
            "#version 120\n" +
                    "\n" +
                    "uniform sampler2D textureIn;\n" +
                    "uniform vec2 texelSize, direction;\n" +
                    "uniform float radius;\n" +
                    "uniform float weights[256];\n" +
                    "\n" +
                    "#define offset texelSize * direction\n" +
                    "\n" +
                    "void main() {\n" +
                    "    vec3 blr = texture2D(textureIn, gl_TexCoord[0].st).rgb * weights[0];\n" +
                    "\n" +
                    "    for (float f = 1.0; f <= radius; f++) {\n" +
                    "        blr += texture2D(textureIn, gl_TexCoord[0].st + f * offset).rgb * (weights[int(abs(f))]);\n" +
                    "        blr += texture2D(textureIn, gl_TexCoord[0].st - f * offset).rgb * (weights[int(abs(f))]);\n" +
                    "    }\n" +
                    "\n" +
                    "    gl_FragColor = vec4(blr, 1.0);\n" +
                    "}";

    public Shaderutil(String fragmentShaderLoc, String vertexShaderLoc) {
        int program = glCreateProgram();
        try {
            int fragmentShaderID;
            switch (fragmentShaderLoc) {
                case "roundedRect":
                    fragmentShaderID = createShader(new ByteArrayInputStream(roundedRect.getBytes()), GL_FRAGMENT_SHADER);
                    break;
                case "roundedRectGradient":
                    fragmentShaderID = createShader(new ByteArrayInputStream(roundedRectGradient.getBytes()), GL_FRAGMENT_SHADER);
                    break;
                case "blurShader":
                    fragmentShaderID = createShader(new ByteArrayInputStream(blurShader.getBytes()), GL_FRAGMENT_SHADER);
                    break;
                default:
                    fragmentShaderID = createShader(mc.getResourceManager().getResource(new ResourceLocation(fragmentShaderLoc)).getInputStream(), GL_FRAGMENT_SHADER);
                    break;
            }
            glAttachShader(program, fragmentShaderID);
            int vertexShaderID = createShader(mc.getResourceManager().getResource(new ResourceLocation(vertexShaderLoc)).getInputStream(), GL_VERTEX_SHADER);
            glAttachShader(program, vertexShaderID);

        } catch (IOException e) {
            e.printStackTrace();
        }

        glLinkProgram(program);
        int status = glGetProgrami(program, GL_LINK_STATUS);

        if (status == 0) {
            throw new IllegalStateException("Shader failed to link!");
        }
        this.programID = program;
    }

    public Shaderutil(String fragmentShaderLoc) {
        this(fragmentShaderLoc, "textures/vertex.vsh");
    }

    public static void setupRoundedRectUniforms(float x, float y, float width, float height, float radius, Shaderutil roundedTexturedShader) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        roundedTexturedShader.setUniformf("location", x * sr.getScaleFactor(), (Minecraft.getMinecraft().displayHeight - (height * sr.getScaleFactor())) - (y * sr.getScaleFactor()));
        roundedTexturedShader.setUniformf("rectSize", width * sr.getScaleFactor(), height * sr.getScaleFactor());
        roundedTexturedShader.setUniformf("radius", radius * sr.getScaleFactor());
    }

    public static void drawQuads(float x, float y, float width, float height) {
        glBegin(GL_QUADS);
        glTexCoord2f(0, 0);
        glVertex2f(x, y);
        glTexCoord2f(0, 1);
        glVertex2f(x, y + height);
        glTexCoord2f(1, 1);
        glVertex2f(x + width, y + height);
        glTexCoord2f(1, 0);
        glVertex2f(x + width, y);
        glEnd();
    }

    public static void drawQuads() {
        ScaledResolution sr = new ScaledResolution(mc);
        float width = (float) sr.getScaledWidth_double();
        float height = (float) sr.getScaledHeight_double();
        glBegin(GL_QUADS);
        glTexCoord2f(0, 1);
        glVertex2f(0, 0);
        glTexCoord2f(0, 0);
        glVertex2f(0, height);
        glTexCoord2f(1, 0);
        glVertex2f(width, height);
        glTexCoord2f(1, 1);
        glVertex2f(width, 0);
        glEnd();
    }

    public static String readInputStream(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null)
                stringBuilder.append(line).append('\n');

        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public void init() {
        glUseProgram(programID);
    }

    public void unload() {
        glUseProgram(0);
    }

    public int getUniform(String name) {
        return glGetUniformLocation(programID, name);
    }

    public void setUniformf(String name, float... args) {
        int loc = glGetUniformLocation(programID, name);
        switch (args.length) {
            case 1:
                glUniform1f(loc, args[0]);
                break;
            case 2:
                glUniform2f(loc, args[0], args[1]);
                break;
            case 3:
                glUniform3f(loc, args[0], args[1], args[2]);
                break;
            case 4:
                glUniform4f(loc, args[0], args[1], args[2], args[3]);
                break;
        }
    }

    public void setUniformi(String name, int... args) {
        int loc = glGetUniformLocation(programID, name);
        if (args.length > 1) glUniform2i(loc, args[0], args[1]);
        else glUniform1i(loc, args[0]);
    }

    private int createShader(InputStream inputStream, int shaderType) {
        int shader = glCreateShader(shaderType);
        glShaderSource(shader, readInputStream(inputStream));
        glCompileShader(shader);

        if (glGetShaderi(shader, GL_COMPILE_STATUS) == 0) {
            System.out.println(glGetShaderInfoLog(shader, 4096));
            throw new IllegalStateException(String.format("Shader (%s) failed to compile!", shaderType));
        }

        return shader;
    }

}