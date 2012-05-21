package com.NFCproject.smartBouncer;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class AccesoBD extends SQLiteOpenHelper {

	String mNombre;
	String mSqlCreate;
    public AccesoBD(Context contexto, String nombre,CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
        this.mNombre = nombre;
      //Sentencia SQL para crear la tabla de acceso
	    mSqlCreate = "CREATE TABLE "+ mNombre +" (_id integer primary key autoincrement,usuario TEXT, empresa TEXT, hora TEXT, fecha TEXT)";
    }
    
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
        //Se ejecuta la sentencia SQL de creación de la tabla		 
		db.execSQL(mSqlCreate);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
		// TODO Auto-generated method stub
// La BD es independiente a la aplicacion, este metodo se ejecuta cuando se modifica la estructura de una BD (se añaden columnas por ejemplo)
// El objetivo es transferir los datos de la BD anterior a la nueva
        //Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS Acceso");
 
        //Se crea la nueva versión de la tabla
        db.execSQL(mSqlCreate);
	}
	
	public void writeBD(String usuario, String empresa, String hora, String fecha) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		// Se comprueba la apertura de la BD
		if(db!=null)
			db.execSQL("INSERT INTO Acceso (usuario, empresa, hora, fecha) VALUES ('" + usuario + "', '" + empresa +"', '"+ hora +"', '"+ fecha +"')");
		
	}
	
	public Cursor readBD(){
		SQLiteDatabase db = this.getReadableDatabase();
		return db.rawQuery(" SELECT * FROM Acceso",null);
	}
		
	public void eraseBD() {
		SQLiteDatabase db = this.getWritableDatabase();
		
		// Se comprueba la apertura de la BD
		if(db!=null)
			db.execSQL("DELETE FROM Acceso");
	}
}
