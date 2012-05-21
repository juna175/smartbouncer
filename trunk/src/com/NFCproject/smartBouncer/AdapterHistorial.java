package com.NFCproject.smartBouncer;


import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class AdapterHistorial extends SimpleCursorAdapter{

	private Activity mContext;
	private Cursor mCursorBD;
	// Almacenamos en esta variable los id de los campos donde escribiremos
	static final int[] mTo = {R.id.stringUsuario,R.id.stringEmpresa,R.id.stringHora,R.id.stringFecha};
	public AdapterHistorial(Activity context,Cursor c) {
		super(context,R.layout.itemhistorial,c,c.getColumnNames(),mTo,FLAG_REGISTER_CONTENT_OBSERVER);
		this.mContext=context;
		this.mCursorBD=c;
	}
	

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub
		super.bindView(view, context, cursor);
		
		// Usuario
		TextView usuario = (TextView)view.findViewById(R.id.stringUsuario);
		usuario.setText(mCursorBD.getString(1));
				
		// Empresa
		TextView empresa = (TextView)view.findViewById(R.id.stringEmpresa);
		empresa.setText(mCursorBD.getString(2));
		
		// Hora
		TextView hora = (TextView)view.findViewById(R.id.stringHora);
		hora.setText(mCursorBD.getString(3));
		
		// Fecha
		TextView fecha = (TextView)view.findViewById(R.id.stringFecha);
		fecha.setText(mCursorBD.getString(4));
		
	}



	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflate = mContext.getLayoutInflater();
		View rowView = inflate.inflate(R.layout.itemhistorial, null);
		return rowView;
	}
	
	
}
