package org.yourorghere;

import com.sun.opengl.util.Animator;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;
import java.io.IOException;
import javax.media.opengl.*;
import javax.media.opengl.glu.*;


public class Bathroom3D implements GLEventListener, KeyListener {

    private Texture floorTexture;
    private Texture wallTexture;
    
    private float rotationAngle = 0.0f; // Sudut rotasi kubus
    private float rotationAngleX = 0.0f; // Sudut rotasi kubus pada sumbu X
    private float cameraX = 0.0f;
    private float cameraZ = 0.0f;
    private float doorRotation = 0.0f; // Sudut rotasi pintu
    private GLCanvas canvas; // Variabel untuk GLCanvas

    public void loadTextures(GL gl) {
        try {
            File floorFile = new File("src/org/yourorghere/wall.jpg");
            BufferedImage floorImage = ImageIO.read(floorFile);
            floorTexture = TextureIO.newTexture(floorImage, true);

            File wallFile = new File("src/org/yourorghere/wood.png");
            BufferedImage wallImage = ImageIO.read(wallFile);
            wallTexture = TextureIO.newTexture(wallImage, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        Frame frame = new Frame("3D Bathroom");
        GLCanvas canvas = new GLCanvas();

        Bathroom3D bathroom = new Bathroom3D(); // Buat objek Bathroom3D
        canvas.addGLEventListener(bathroom); // Tambahkan sebagai GLEventListener
        canvas.addKeyListener(bathroom); // Tambahkan sebagai KeyListener
        bathroom.setCanvas(canvas); // Set canvas pada objek bathroom
        frame.add(canvas);
        frame.setSize(800, 600);
        final Animator animator = new Animator(canvas);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                new Thread(new Runnable() {

                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animator.start();
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        System.err.println("INIT GL IS: " + gl.getClass().getName());
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);
        gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
        loadTextures(gl); // Load textures
        
    }

    
     @Override
public void display(GLAutoDrawable drawable) {
    GL gl = drawable.getGL();

    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
    gl.glLoadIdentity();
    GLU glu = new GLU();
    
    // Set the camera position
    glu.gluLookAt(0.0, 0.0, 5.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);

    // Draw 3D Cube
    gl.glTranslatef(0.0f, 0.0f, -6.0f);
    gl.glTranslatef(cameraX, 0.0f, cameraZ); // Menggeser posisi kamera
    gl.glRotatef(rotationAngleX, 1.0f, 0.0f, 0.0f); // Rotasi pada sumbu X
    gl.glRotatef(rotationAngle, 0.0f, 1.0f, 0.0f); // Rotasi pada sumbu Y
    gl.glBegin(GL.GL_QUADS);
    

//    gl.glColor3f(0.0f, 1.0f, 1.0f); // Cyan
//    gl.glBegin(GL.GL_QUADS);

gl.glEnable(GL.GL_TEXTURE_2D);
wallTexture.bind(); // Bind the loaded texture
gl.glBegin(GL.GL_QUADS); 
    gl.glColor3f(1.0f, 1.0f, 1.0f); 
    gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-1.5f, -1.0f, -1.0f); // Bottom Left
gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(1.2f, -1.0f, -1.0f);  // Bottom Right
gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(1.2f, 1.0f, -1.0f);   // Top Right
gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-1.5f, 1.0f, -1.0f);  // Top Left
gl.glEnd();
    gl.glDisable(GL.GL_TEXTURE_2D);

    
//    gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-1.5f, -1.0f, -1.0f); // Bottom Left
//gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(1.2f, -1.0f, -1.0f);  // Bottom Right
//gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(1.2f, 1.0f, -1.0f);   // Top Right
//gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-1.5f, 1.0f, -1.0f);  // Top Left
    
//    // Draw Floor with Texture
gl.glEnable(GL.GL_TEXTURE_2D);
wallTexture.bind(); // Bind the loaded texture
gl.glBegin(GL.GL_QUADS);
gl.glColor3f(1.0f, 1.0f, 1.0f);
//    gl.glBegin(GL.GL_QUADS);
gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-1.5f, -1.0f, -1.0f); // Bottom Left
gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-1.5f, -1.0f, 1.5f); // Top Left
gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(1.2f, -1.0f, 1.5f); // Top Right
gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(1.2f, -1.0f, -1.0f); // Bottom Right
    gl.glEnd();
    gl.glDisable(GL.GL_TEXTURE_2D);

    // Draw Left face (Cyan Wall)
//    gl.glColor3f(0.0f, 1.0f, 1.0f); // Cyan
//    gl.glBegin(GL.GL_QUADS);

gl.glEnable(GL.GL_TEXTURE_2D);
floorTexture.bind(); // Bind the loaded texture
gl.glBegin(GL.GL_QUADS);
    gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-1.5f, -1.0f, -1.0f); // Bottom Front
    gl.glTexCoord2f(0.0f, 1.0f);gl.glVertex3f(-1.5f, -1.0f, 1.5f); // Top Front
    gl.glTexCoord2f(1.0f, 1.0f);gl.glVertex3f(-1.5f, 1.0f, 1.5f); // Top Back
    gl.glTexCoord2f(1.0f, 0.0f);gl.glVertex3f(-1.5f, 1.0f, -1.0f); // Bottom Back
    gl.glEnd();
gl.glDisable(GL.GL_TEXTURE_2D);
    
    // Draw Door
    gl.glPushMatrix();
    gl.glTranslatef(-0.1f, -1.0f, -0.99f); // Pintu diletakkan di depan dinding, sejajar dengan lantai
    gl.glRotatef(doorRotation, 0.0f, 1.0f, 0.0f); // Rotasi pintu
    gl.glColor3f(0.5f, 0.35f, 0.05f); // Brown
    gl.glBegin(GL.GL_QUADS);
    gl.glVertex3f(-0.2f, 0.0f, 0.0f);
    gl.glVertex3f(0.2f, 0.0f, 0.0f);
    gl.glVertex3f(0.2f, 1.0f, 0.01f); // Mengubah tinggi pintu menjadi 1.0f
    gl.glVertex3f(-0.2f, 1.0f, 0.0f); // Mengubah koordinat x menjadi -0.2f
    gl.glEnd();
    gl.glPopMatrix();
    
    // Draw Bathtub
    gl.glPushMatrix();
    gl.glTranslatef(0.0f, -0.96f, 1.0f); // Menempatkan bak mandi di tengah ruangan, sedikit di atas lantai
    gl.glColor3f(0.5f, 0.5f, 0.5f); // Abu-abu
    gl.glEnable(GL.GL_TEXTURE_2D);
//bathtubTexture.bind(); // Mengikat tekstur yang dimuat

    // Draw the faces of the bathtub
    // Front face (outer)
gl.glBegin(GL.GL_QUADS);
gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-0.45f, 0.0f, 0.35f);
gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(0.45f, 0.0f, 0.35f);
gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(0.45f, 0.5f, 0.35f);
gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-0.45f, 0.5f, 0.35f);
gl.glEnd();

// Front face (inner)
gl.glBegin(GL.GL_QUADS);
gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-0.4f, 0.0f, 0.3f);
gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(0.4f, 0.0f, 0.3f);
gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(0.4f, 0.5f, 0.3f);
gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-0.4f, 0.5f, 0.3f);
gl.glEnd();

// Top face (front)
gl.glBegin(GL.GL_QUADS);
gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-0.45f, 0.5f, 0.35f);
gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(0.45f, 0.5f, 0.35f);
gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(0.4f, 0.5f, 0.3f);
gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-0.4f, 0.5f, 0.3f);
gl.glEnd();

// Back face (outer)
gl.glBegin(GL.GL_QUADS);
gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-0.45f, 0.0f, -0.35f);
gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(0.45f, 0.0f, -0.35f);
gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(0.45f, 0.5f, -0.35f);
gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-0.45f, 0.5f, -0.35f);
gl.glEnd();

// Back face (inner)
gl.glBegin(GL.GL_QUADS);
gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-0.4f, 0.0f, -0.3f);
gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(0.4f, 0.0f, -0.3f);
gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(0.4f, 0.5f, -0.3f);
gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-0.4f, 0.5f, -0.3f);
gl.glEnd();

    // Top face (back)
gl.glBegin(GL.GL_QUADS);
gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-0.45f, 0.5f, -0.35f);
gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(0.45f, 0.5f, -0.35f);
gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(0.4f, 0.5f, -0.3f);
gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-0.4f, 0.5f, -0.3f);
gl.glEnd();

// Left face (outer)
gl.glBegin(GL.GL_QUADS);
gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-0.45f, 0.0f, -0.35f);
gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-0.45f, 0.0f, 0.35f);
gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-0.45f, 0.5f, 0.35f);
gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-0.45f, 0.5f, -0.35f);
gl.glEnd();

// Left face (inner)
gl.glBegin(GL.GL_QUADS);
gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-0.4f, 0.0f, -0.3f);
gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-0.4f, 0.0f, 0.3f);
gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-0.4f, 0.5f, 0.3f);
gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-0.4f, 0.5f, -0.3f);
gl.glEnd();

// Top face (left)
gl.glBegin(GL.GL_QUADS);
gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-0.45f, 0.5f, -0.35f);
gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-0.45f, 0.5f, 0.35f);
gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-0.4f, 0.5f, 0.3f);
gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-0.4f, 0.5f, -0.3f);
gl.glEnd();
    
    // Right face (outer)
gl.glBegin(GL.GL_QUADS);
gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(0.45f, 0.0f, -0.35f);
gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(0.45f, 0.0f, 0.35f);
gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(0.45f, 0.5f, 0.35f);
gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(0.45f, 0.5f, -0.35f);
gl.glEnd();

// Right face (inner)
gl.glBegin(GL.GL_QUADS);
gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(0.4f, 0.0f, -0.3f);
gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(0.4f, 0.0f, 0.3f);
gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(0.4f, 0.5f, 0.3f);
gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(0.4f, 0.5f, -0.3f);
gl.glEnd();

// Top face (right)
gl.glBegin(GL.GL_QUADS);
gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(0.45f, 0.5f, -0.35f);
gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(0.45f, 0.5f, 0.35f);
gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(0.4f, 0.5f, 0.3f);
gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(0.4f, 0.5f, -0.3f);
gl.glEnd();

    // Top face (right)
    gl.glBegin(GL.GL_QUADS);
    gl.glVertex3f(0.45f, 0.5f, -0.35f);
    gl.glVertex3f(0.45f, 0.5f, 0.35f);
    gl.glVertex3f(0.4f, 0.5f, 0.3f);
    gl.glVertex3f(0.4f, 0.5f, -0.3f);
    gl.glEnd();

    // Draw lines around the edges of the bathtub
    gl.glLineWidth(2.0f); // Increase line width
    gl.glColor3f(0.0f, 0.0f, 0.0f); // Black

       gl.glEnable(GL.GL_TEXTURE_2D); // Enable texture mapping
    // Front face (outer)
    gl.glBegin(GL.GL_LINE_LOOP);
    gl.glTexCoord2f(0.0f, 0.0f);gl.glVertex3f(-0.45f, 0.0f, 0.35f);
    gl.glTexCoord2f(1.0f, 0.0f);gl.glVertex3f(0.45f, 0.0f, 0.35f);
    gl.glTexCoord2f(1.0f, 1.0f);gl.glVertex3f(0.45f, 0.5f, 0.35f);
    gl.glTexCoord2f(1.0f, 1.0f);gl.glVertex3f(-0.45f, 0.5f, 0.35f);
    gl.glEnd();

    // Front face (inner)
    gl.glEnable(GL.GL_TEXTURE_2D); // Enable texture mapping
    gl.glBegin(GL.GL_LINE_LOOP);
    gl.glTexCoord2f(0.0f, 0.0f);gl.glVertex3f(-0.4f, 0.0f, 0.3f);
    gl.glTexCoord2f(1.0f, 0.0f);gl.glVertex3f(0.4f, 0.0f, 0.3f);
    gl.glTexCoord2f(1.0f, 1.0f);gl.glVertex3f(0.4f, 0.5f, 0.3f);
    gl.glTexCoord2f(1.0f, 1.0f);gl.glVertex3f(-0.4f, 0.5f, 0.3f);
    gl.glEnd();

    // Back face (outer)
    gl.glBegin(GL.GL_LINE_LOOP);
    gl.glVertex3f(-0.45f, 0.0f, -0.35f);
    gl.glVertex3f(0.45f, 0.0f, -0.35f);
    gl.glVertex3f(0.45f, 0.5f, -0.35f);
    gl.glVertex3f(-0.45f, 0.5f, -0.35f);
    gl.glEnd();

    // Back face (inner)
    gl.glBegin(GL.GL_LINE_LOOP);
    gl.glVertex3f(-0.4f, 0.0f, -0.3f);
    gl.glVertex3f(0.4f, 0.0f, -0.3f);
    gl.glVertex3f(0.4f, 0.5f, -0.3f);
    gl.glVertex3f(-0.4f, 0.5f, -0.3f);
    gl.glEnd();

    // Left face (outer)
    gl.glBegin(GL.GL_LINE_LOOP);
    gl.glVertex3f(-0.45f, 0.0f, -0.35f);
    gl.glVertex3f(-0.45f, 0.0f, 0.35f);
    gl.glVertex3f(-0.45f, 0.5f, 0.35f);
    gl.glVertex3f(-0.45f, 0.5f, -0.35f);
    gl.glEnd();

        // Right face (outer)
        gl.glBegin(GL.GL_LINE_LOOP);
        gl.glVertex3f(0.45f, 0.0f, -0.35f);
        gl.glVertex3f(0.45f, 0.0f, 0.35f);
        gl.glVertex3f(0.45f, 0.5f, 0.35f);
        gl.glVertex3f(0.45f, 0.5f, -0.35f);
        gl.glEnd();

        // Draw lines for the edges of the top face
        gl.glBegin(GL.GL_LINES);
        gl.glVertex3f(-0.45f, 0.5f, 0.35f);
        gl.glVertex3f(-0.45f, 0.5f, -0.35f);

        gl.glVertex3f(0.45f, 0.5f, 0.35f);
        gl.glVertex3f(0.45f, 0.5f, -0.35f);

        gl.glVertex3f(-0.4f, 0.5f, 0.3f);
        gl.glVertex3f(-0.4f, 0.5f, -0.3f);

        gl.glVertex3f(0.4f, 0.5f, 0.3f);
        gl.glVertex3f(0.4f, 0.5f, -0.3f);
        gl.glEnd();

        gl.glPopMatrix();
        
        
        // Draw Wastapel
        gl.glPushMatrix();
        gl.glTranslatef(-1.3f, -1.0f, -0.4f); // Posisi wastafel
        gl.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f); // Rotasi agar wastafel menghadap ke atas
        gl.glColor3f(0.5f, 0.5f, 0.5f); // Abu-abu

        // Objek quadric untuk bagian luar
        GLUquadric qOuter = glu.gluNewQuadric();
        glu.gluQuadricNormals(qOuter, GLU.GLU_SMOOTH); // Atur normal vektor untuk objek quadric

        // Menggambar silinder luar untuk wastafel dengan radius yang lebih kecil
        glu.gluCylinder(qOuter, 0.2f, 0.2f, 0.45f, 32, 32);

        // Objek quadric untuk bagian dalam
        GLUquadric qInner = glu.gluNewQuadric();
        glu.gluQuadricNormals(qInner, GLU.GLU_SMOOTH); // Atur normal vektor untuk objek quadric

        // Menggambar silinder dalam untuk wastafel dengan radius yang lebih kecil
        gl.glTranslatef(0.0f, 0.0f, 0.01f); // Sedikit bergeser ke atas agar tidak tumpang tindih
        glu.gluCylinder(qInner, 0.18f, 0.18f, 0.43f, 32, 32);

        // Tutup bagian atas sisi tebal
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 0.0f, 0.45f); // Geser ke atas di ujung silinder luar
        gl.glColor3f(0.5f, 0.5f, 0.5f); // Warna abu-abu sama seperti silinder
        glu.gluDisk(qOuter, 0.18f, 0.2f, 32, 1); // Tutup sisi tebal bagian atas dengan disk kecil, hanya sisi tebal
        gl.glPopMatrix();

        // Menambahkan garis di tepi atas wastafel untuk mempertegas kesan 3D
        gl.glLineWidth(2.0f); // Atur ketebalan garis
        gl.glColor3f(0.0f, 0.0f, 0.0f); // Hitam

        // Garis di tepi atas bagian luar
        gl.glBegin(GL.GL_LINE_LOOP);
        for (int i = 0; i < 32; i++) {
            double angle = 2 * Math.PI * i / 32;
            gl.glVertex3f((float)(0.2 * Math.cos(angle)), (float)(0.2 * Math.sin(angle)), 0.45f);
        }
        gl.glEnd();

        // Garis di tepi atas bagian dalam
        gl.glBegin(GL.GL_LINE_LOOP);
        for (int i = 0; i < 32; i++) {
            double angle = 2 * Math.PI * i / 32;
            gl.glVertex3f((float)(0.18 * Math.cos(angle)), (float)(0.18 * Math.sin(angle)), 0.45f);
        }
        gl.glEnd();

        // Garis melingkar horizontal di badan silinder
        gl.glBegin(GL.GL_LINE_LOOP);
        float height = 0.225f; // Ketinggian garis melingkar di sepanjang sumbu z
        for (int i = 0; i < 32; i++) {
            double angle = 2 * Math.PI * i / 32;
            gl.glVertex3f((float)(0.2 * Math.cos(angle)), (float)(0.2 * Math.sin(angle)), height);
        }
        gl.glEnd();
//        gl.glPopMatrix
                
                gl.glPopMatrix();
                
                // draw Cermin 
        gl.glPushMatrix();
        gl.glTranslatef(-1.5f, -0.13f, -0.4f); // Posisi wastafel
        gl.glRotatef(90.0f, 0.0f, 1.0f, 0.0f); // Rotasi agar wastafel menghadap ke depan
        gl.glColor3f(0.5f, 0.5f, 0.5f); // Abu-abu

        // Objek quadric untuk bagian luar
        GLUquadric qOuter2 = glu.gluNewQuadric();
        glu.gluQuadricNormals(qOuter2, GLU.GLU_SMOOTH); // Atur normal vektor untuk objek quadric

        // Menggambar silinder luar untuk wastafel dengan radius yang lebih kecil
        glu.gluCylinder(qOuter2, 0.15f, 0.15f, 0.05f, 32, 32); // Ubah nilai radius dan tinggi

        // Objek quadric untuk bagian dalam
        GLUquadric qInner2 = glu.gluNewQuadric();
        glu.gluQuadricNormals(qInner2, GLU.GLU_SMOOTH); // Atur normal vektor untuk objek quadric

        // Menggambar silinder dalam untuk wastafel dengan radius yang lebih kecil
        gl.glTranslatef(0.0f, 0.0f, 0.01f); // Sedikit bergeser ke atas agar tidak tumpang tindih
        glu.gluCylinder(qOuter2, 0.15f, 0.15f, 0.05f, 32, 32); // Ubah nilai radius dan tinggi

        // Tutup bagian atas sisi tebal
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 0.0f, 0.05f); // Geser ke atas di ujung silinder luar
        gl.glColor3f(0.5f, 0.5f, 0.5f); // Warna abu-abu sama seperti silinder
        glu.gluDisk(qOuter, 0.13f, 0.15f, 32, 1); // Tutup sisi tebal bagian atas dengan disk kecil, hanya sisi tebal
        gl.glPopMatrix();

        // Menggambar lingkaran di bagian tengah dengan warna putih
         gl.glColor4f(0.0f, 1.0f, 1.0f, 1.0f); // Biru terang

        gl.glTranslatef(0.0f, 0.0f, 0.06f); // Geser ke atas untuk berada di atas bagian tengah
        glu.gluDisk(qOuter, 0.0f, 0.13f, 32, 1); // Menggambar lingkaran di tengah dengan radius sesuai dengan radius bagian dalam

        // Menambahkan garis di tepi atas wastafel untuk mempertegas kesan 3D
        gl.glLineWidth(2.0f); // Atur ketebalan garis
        gl.glColor3f(0.0f, 0.0f, 0.0f); // Hitam

        // Garis di tepi atas bagian luar
        gl.glBegin(GL.GL_LINE_LOOP);
        for (int i = 0; i < 32; i++) {
            double angle = 2 * Math.PI * i / 32;
            gl.glVertex3f((float)(0.15 * Math.cos(angle)), (float)(0.15 * Math.sin(angle)), 0.0f);
        }
        gl.glEnd();

        // Garis di tepi atas bagian dalam
        gl.glBegin(GL.GL_LINE_LOOP);
        for (int i = 0; i < 32; i++) {
            double angle = 2 * Math.PI * i / 32;
            gl.glVertex3f((float)(0.13 * Math.cos(angle)), (float)(0.13 * Math.sin(angle)), 0.0f);
        }
        gl.glEnd();

        gl.glPopMatrix();
        
        // draw Shower
gl.glPushMatrix();
gl.glTranslatef(-1.5f, 0.19f, 1.1f); // Posisi shower
gl.glRotatef(90.0f, 0.0f, 1.0f, 0.0f); // Rotasi agar shower menghadap ke depan
gl.glColor3f(0.5f, 0.5f, 0.5f); // Abu-abu

// Objek quadric untuk bagian lurus
GLUquadric qOuter3 = glu.gluNewQuadric();
glu.gluQuadricNormals(qOuter3, GLU.GLU_SMOOTH); // Atur normal vektor untuk objek quadric

// Menggambar silinder lurus untuk shower dengan radius yang lebih kecil dan tinggi yang lebih panjang
glu.gluCylinder(qOuter3, 0.015f, 0.015f, 0.35f, 32, 32); // Ubah nilai radius menjadi lebih kecil

// Membuat bagian melengkung
gl.glPushMatrix();
gl.glTranslatef(0.0f, 0.0f, 0.3f); // Geser ke ujung silinder lurus
for (int i = 0; i < 10; i++) {
    gl.glTranslatef(0.0f, 0.0f, 0.03f); // Geser sedikit ke depan
    gl.glRotatef(9.0f, 1.0f, 0.0f, 0.0f); // Rotasi sedikit ke bawah
    glu.gluCylinder(qOuter3, 0.015f, 0.015f, 0.03f, 32, 32); // Menggambar segmen silinder pendek
}

// Tutup bagian ujung bawah
gl.glTranslatef(0.0f, 0.0f, 0.05f); // Geser ke ujung silinder pendek
//glu.gluDisk(qOuter3, 0.0f, 0.015f, 32, 1); // Tutup ujung bawah dengan disk
gl.glPopMatrix(); // Pop transformasi untuk lengkungan
gl.glPopMatrix(); // Pop transformasi utama

     // draw very small cone facing upward
gl.glPushMatrix();
gl.glTranslatef(-1.0f, -0.1f, 1.1f); // Posisi kerucut
gl.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f); // Rotasi agar kerucut menghadap ke atas
gl.glColor3f(0.5f, 0.5f, 0.5f);

// Objek quadric untuk kerucut
GLUquadric qCone = glu.gluNewQuadric();
glu.gluQuadricNormals(qCone, GLU.GLU_SMOOTH); // Atur normal vektor untuk objek quadric

// Menggambar kerucut menghadap ke atas dengan radius atas lebih besar dan radius bawah lebih kecil
glu.gluCylinder(qCone, 0.11f, 0.0f, 0.10f, 8, 8); // Radius atas 0.02, radius bawah 0.0, tinggi 0.05

gl.glPopMatrix(); // Pop transformasi utama

}

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();
        GLU glu = new GLU();

        if (height <= 0) {
            height = 1;
        }
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, h, 1.0, 100.0);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Tekan tombol panah kiri untuk memutar kubus ke kiri
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            rotationAngle -= 2.0f;
        }
        // Tekan tombol panah kanan untuk memutar kubus ke kanan
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rotationAngle += 2.0f;
        }
        // Tekan tombol panah atas untuk memutar kubus ke atas
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            rotationAngleX -= 2.0f;
        }
        // Tekan tombol panah bawah untuk memutar kubus ke bawah
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            rotationAngleX += 2.0f;
        }
        // Tekan tombol W untuk maju (bergerak ke depan)
        if (e.getKeyCode() == KeyEvent.VK_W) {
            cameraZ += 0.1f;
        }
        // Tekan tombol S untuk mundur (bergerak ke belakang)
        if (e.getKeyCode() == KeyEvent.VK_S) {
            cameraZ -= 0.1f;
        }
        // Tekan tombol A untuk geser ke kiri
        if (e.getKeyCode() == KeyEvent.VK_D) {
            cameraX -= 0.1f;
        }
        // Tekan tombol D untuk geser ke kanan
        if (e.getKeyCode() == KeyEvent.VK_A) {
            cameraX += 0.1f;
        }
        // Tekan tombol P untuk membuka/menutup pintu
        if (e.getKeyCode() == KeyEvent.VK_P) {
            doorRotation += 5.0f;
            if (doorRotation > 90.0f) {
                doorRotation = 90.0f;
            }
        }
        canvas.repaint(); // Gambar ulang setelah rotasi diubah
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public void setCanvas(GLCanvas canvas) {
        this.canvas = canvas;
    }
}
