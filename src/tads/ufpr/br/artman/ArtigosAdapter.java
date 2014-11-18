package tads.ufpr.br.artman;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public abstract class ArtigosAdapter extends BaseAdapter {
	
	private List<Artigo> artigos;
	private LayoutInflater inflater;
	
	public ArtigosAdapter(Context context, List<Artigo> artigos) {
		this.inflater = (LayoutInflater)
				context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.artigos = artigos;
	}
	
	
	
	public void novosDados(List<Artigo> artigos) {
		this.artigos = artigos;
		//atualiza o adapter sempre q insere,deleta ou edita algo da lista
		notifyDataSetChanged();
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return artigos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return artigos.get(position);
	}

	@Override
	//Retorna um id específico para cada elemento da lista
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	

	@Override
	public View getView(final int position, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.item_artigo, null);
		((TextView)(v.findViewById(R.id.txtNome))).setText(artigos.get(position).nome);
		((ImageButton)(v.findViewById(R.id.btnEditar))).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				edita(artigos.get(position));
			}
		});
		((ImageButton)(v.findViewById(R.id.btnExcluir))).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				deleta(artigos.get(position));
			}
		});
		return v;
	}
	
	public abstract void edita(Artigo artigo);
	
	public abstract void deleta(Artigo artigo);
	
	

}
