package tads.ufpr.br.artman;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


//REALIZAR A INTEGRAÇÃO ENTRE O ADAPTER E O BANCO DE DADOS
public class ArtigosActivity extends ListActivity {
	private BancoDeDados db;
	private List<Artigo> artigos = new ArrayList<Artigo>();
	private ArtigosAdapter artigosAdapter;
	public static final int REQUEST_EDICAO = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artigos);
        db = new BancoDeDados(this);
        lerDados();
    }
    
    
    //Abre a BaseDeDados, limpa o List que apresentará os dados na tela
    //Método retornaTodosArtigos é chamado,armazenando seu retorno em uma instância de Cursor
    //Tentamos mover o cursor para o 1ºelemento, if possible,tabela não vazia
    //Entramos no laço do-while,q adiciona cada instância de Artigo do List
    public void lerDados(){
    	db.abrir();
    	artigos.clear();
    	Cursor cursor = db.retornaTodosArtigos();
    	if (cursor.moveToFirst())
    		do {
    			Artigo a = new Artigo();
    			a.id = cursor.getInt(cursor.getColumnIndex(BancoDeDados.KEY_ID));
    			a.nome = cursor.getString(cursor.getColumnIndex(BancoDeDados.KEY_NOME));
    			a.revista = cursor.getString(cursor.getColumnIndex(BancoDeDados.KEY_REVISTA));
    			a.edicao = cursor.getString(cursor.getColumnIndex(BancoDeDados.KEY_EDICAO));
    			a.status = cursor.getInt(cursor.getColumnIndex(BancoDeDados.KEY_STATUS));
    			a.pago = cursor.getInt(cursor.getColumnIndex(BancoDeDados.KEY_PAGO));
    			artigos.add(a);
    		} while (cursor.moveToNext());
      
    //Verificar se busca trouxe algum registro e se mudou tam da lista para > 0
    //Se sim,verificamos se o artigosAdapter ainda está nulo
    //Se sim,método lerDados nunca foi chamado antes e o adapter deve ser instanciado
    //Fornecemos a implementação dosmétodos edita e deleta
    if (artigos.size() > 0) {
    	if (artigosAdapter == null){
    		artigosAdapter = new ArtigosAdapter(this, artigos) {
    			@Override
    			public void edita(Artigo artigo) {
    				// TODO Auto-generated method stub
    				Intent intent = new Intent(getApplicationContext(), NovoEdicaoActivity.class);
    				intent.putExtra("artigo", artigo);
    				//Descobrir se o user pressionou salvar ou cancelou a ação
    				startActivityForResult(intent, REQUEST_EDICAO);
    			}
    			
    			@Override
    			public void deleta(Artigo artigo) {
    				// TODO Auto-generated method stub
    				db.abrir();
    				db.apagaArtigo(artigo.id);
    				db.fechar();
    				lerDados();
    			}
    		};
    		setListAdapter(artigosAdapter);
    	} else
    		artigosAdapter.novosDados(artigos);
    }
    db.fechar();
}
    

@Override
public boolean onCreateOptionsMenu(Menu menu) {
	// TODO Auto-generated method stub
	getMenuInflater().inflate(R.menu.main, menu);
	return true;
}


@Override
public boolean onMenuItemSelected(int featureId, MenuItem item) {
	// TODO Auto-generated method stub
	if (item.getItemId() == R.id.menu_add){
		Intent intent = new Intent(this, NovoEdicaoActivity.class);
		startActivityForResult(intent, REQUEST_EDICAO);
		return true;
	} else {
		return super.onMenuItemSelected(featureId, item);
	}
}


public static final int REQUEST_SALVOU = 1;

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	// TODO Auto-generated method stub
	if (requestCode == REQUEST_EDICAO)
		if(resultCode == REQUEST_SALVOU)
			//User inseriu ou editou - gravou - dados
			lerDados();
}




}
