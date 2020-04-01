package com.example.finalproyect_allengram.ModeloDatos;

import com.google.firebase.database.IgnoreExtraProperties;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

@IgnoreExtraProperties
public class User {
    private ArrayList<String>sigues;
    private ArrayList<Publicacion> publicacionesCronologia;
    private String username, pass;
    private int theme;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, ArrayList<Publicacion> publicacionesCronologia,ArrayList<String>sigues) {
        this.sigues=sigues;
        this.username = username;
        this.publicacionesCronologia = publicacionesCronologia;
    }

    public User(String username, String pass, int theme) {
        this.username = username;
        this.pass = hashContraseña(pass, "75" + pass + "RT·$");
        this.theme = theme;
    }

    private String hashContraseña(String pass, String seed) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(seed.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(pass.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<String> getSigues() {
        return sigues;
    }

    public void setSigues(ArrayList<String> sigues) {
        this.sigues = sigues;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getTheme() {
        return theme;
    }

    public void setTheme(int theme) {
        this.theme = theme;
    }

    public ArrayList<Publicacion> getPublicacionesCronologia() {
        return publicacionesCronologia;
    }

    public void setPublicacionesCronologia(ArrayList<Publicacion> publicacionesCronologia) {
        this.publicacionesCronologia = publicacionesCronologia;
    }

    @Override
    public String toString() {
        return "User{" +
                "publicacionesCronologia=" + publicacionesCronologia +
                ", username='" + username + '\'' +
                '}';
    }
}