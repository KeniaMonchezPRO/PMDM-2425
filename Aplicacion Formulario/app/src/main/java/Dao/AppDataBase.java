package Dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import Entities.Usuario;

@Database(entities = {Usuario.class}, version = 2, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase instancia;

    //definimos todos los dao
    public abstract UsuarioDao usuarioDao();

    public static synchronized AppDataBase getInstance(Context context) {
        //si no esta creada una instancia la crea, sino la devuelve
        if (instancia == null) {
            instancia = Room.databaseBuilder(context.getApplicationContext(),
                            AppDataBase.class, "meu_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries() // importante: so para probas, en entorno de produccion se quita, por la concurrencia
                    .build();
        }
        return instancia;
    }
}
