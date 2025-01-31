package Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import Entities.Usuario;

@Dao
public interface UsuarioDao {
    @Insert
    void insertar(Usuario usuario);
    @Query("SELECT * FROM usuarios")
    List<Usuario> obtenerTodos();
}
