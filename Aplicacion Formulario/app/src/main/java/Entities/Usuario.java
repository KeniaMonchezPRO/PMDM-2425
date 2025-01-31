package Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "usuarios") //que la entidad está mapeada na bd con el nombre de la tabla
public class Usuario {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nombre;
    private String apelidos;
    private String email;
    private String mobil;
    private String contrasinal;
    private boolean publicidade;

    public Usuario(String nombre, String apelidos, String email, String mobil, String contrasinal, boolean publicidade) {
        this.nombre = nombre;
        this.apelidos = apelidos;
        this.email = email;
        this.mobil = mobil;
        this.contrasinal = contrasinal;
        this.publicidade = publicidade;
    }

    public String getApelidos() {
        return apelidos;
    }

    public String getMobil() {
        return mobil;
    }

    public String getContrasinal() {
        return contrasinal;
    }

    public boolean isPublicidade() {
        return publicidade;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setApelidos(String apelidos) {
        this.apelidos = apelidos;
    }

    public void setMobil(String mobil) {
        this.mobil = mobil;
    }

    public void setContrasinal(String contrasinal) {
        this.contrasinal = contrasinal;
    }

    public void setPublicidade(boolean publicidade) {
        this.publicidade = publicidade;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apelidos='" + apelidos + '\'' +
                ", email='" + email + '\'' +
                ", mobil='" + mobil + '\'' +
                ", contrasinal='" + contrasinal + '\'' +
                ", publicidade=" + publicidade +
                '}';
    }
}
