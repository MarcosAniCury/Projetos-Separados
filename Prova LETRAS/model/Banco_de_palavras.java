package model;

//a logica dessa classe e querendo adicionar mais palavras basta adicionar no final da string e caso deseje remover, so retirar da string
public class Banco_de_palavras
{
    private ScorePalavra[] palavras = new ScorePalavra[70];

    public Banco_de_palavras() 
    {
        String frase = " Abacaxi,Manada,mandar,porta,mesa,Dado,Mangas,Já,coisas,radiografia,matemática,Drogas,prédios,implementação,computador,balão,Xícara,Tédio,faixa,Livro,deixar,superior,Profissão,Reunião,Prédios,Montanha,Botânica,Banheiro,Caixas,Xingamento,Infestação,Cupim,Premiada,empanada,Ratos,Ruído,Antecedente,Empresa,Emissário,Folga,Fratura,Goiaba,Gratuito,Hídrico,Homem,Jantar,Jogos,Montagem,Manual,Nuvem,Neve,Operação,Ontem,Pato,Pé,viagem,Queijo,Quarto,Quintal,Solto,rota,Selva,Tatuagem,Tigre,Uva,Último,Vitupério,Voltagem,Zangado,Zombaria,Dor";
        Armazenar(frase);
        //ordenar as palavras da menor pontuacao para a maior para diminuir o numero de interacoes no metodo principal
        ordenarQuicksort(0, palavras.length - 1);
    }

    private void Armazenar(String frase)
    {
        //pegar uma a uma palavra e armazenar na no vetor de ScorePalavra
        int comeco = 0;
        int fim = 0;
        String palavra = "";
        for (int i = 0;i < this.palavras.length;i++)
        {
            comeco = fim + 1;
            fim = frase.indexOf(",",fim + 1);
            palavra = frase.substring(comeco, fim);
            palavra = palavra.toLowerCase();
            palavras[i] = new ScorePalavra(palavra);
        }
        palavra = frase.substring(fim + 1, frase.length());
        palavra = palavra.toLowerCase();
        palavras[this.palavras.length - 1] = new ScorePalavra(palavra);  
    }

    public String getPalavra(int pos)
    {
        String retorno = "";
        if (pos > 0)
            retorno = palavras[pos].getPalavra();
        return retorno;
    }

    public int getPontuacao(int pos)
    {
        int retorno = 0;
        if (pos > 0)
            retorno = palavras[pos].getPontuacao();
        return retorno;
    }

    public int getTam()
    {
        return palavras.length;
    }

    public int pesquisaSeqPontuacao(String palavra)
    {
        int retorno = 0;
        int i = 0;
        do
        {
            if (palavras[i].getPalavra().equals(palavra))
                retorno = palavras[i].getPontuacao();
            i++;
        }while(i < palavras.length && retorno == 0);
        return retorno;
    }

    //metodo quicksort ordenando objetos pela sua pontuacao
    private void ordenarQuicksort(int esq, int dir)
    {
        int i = esq;
        int j = dir;
        int pivo = palavras[(esq+dir)/2].getPontuacao();
        while (i <= j)
        {
            while(palavras[i].getPontuacao() < pivo)
                i++;
            while(palavras[j].getPontuacao() > pivo)
                j--;
            if (i <= j)
            {
                if (palavras[i].getPontuacao() == palavras[j].getPontuacao() && palavras[i].getPalavra().length() > palavras[j].getPalavra().length())
                    troca(i,j);
                else
                    troca(i,j);
                i++;
                j--;
            }
        }
        if (esq < j)
            ordenarQuicksort(esq, j);
        if (dir > i)
            ordenarQuicksort(i, dir);
    }

    private void troca (int i,int j)
    {
        ScorePalavra temp = palavras[i];
        palavras[i] = palavras[j];
        palavras[j] = temp;
    }

    private void ToString()
    {
        for (int i = 0; i < palavras.length;i++)
            System.out.printf("Palavra:%s\nPontuacao:%d\n\n", palavras[i].getPalavra(),palavras[i].getPontuacao());
    }
}

class ScorePalavra
{
    private String palavra;
    private int pontuacao;

    public ScorePalavra()
    {
        palavra = null;
        pontuacao = 0;
    }

    public ScorePalavra(String palavra)
    {
        this.palavra = palavra;
        pontuacaoPalavra();
    }

    public void setPalavra(String palavra)
    {
        this.palavra = palavra;
        //armazenar a pontuacao da palavra
        pontuacaoPalavra();
    }

    public String getPalavra()
    {
        return this.palavra;
    }

    public int getPontuacao()
    {
        return this.pontuacao;
    }

    private void pontuacaoPalavra()
    {
        pontuacao = 0;
        for (int i = 0;i < palavra.length();i++)
        {
            char letra = palavra.charAt(i);
            pontuacao += pontuacaoLetra(letra);
        }
    }

    private int pontuacaoLetra(char letra)
    {
        pontuacao = 0;
        if (letra == 'q' || letra == 'z')
            pontuacao = 10;
        else if (letra == 'j' || letra == 'x')
            pontuacao = 8;
        else if (letra == 'f' || letra == 'h' || letra == 'v')
            pontuacao = 4;
        else if (letra == 'b' || letra == 'c' || letra == 'm' || letra == 'p')
            pontuacao = 3;
        else if (letra == 'w' || letra == 'd' || letra == 'g')
            pontuacao = 2;
        else if (letra == 'e' || letra == 'a' || letra == 'i' || letra == 'o' || letra == 'n' || letra == 'r' || letra == 't' || letra == 'l' || letra == 's' || letra == 'u')
            pontuacao = 1;
        else
            pontuacao = 0;
        return pontuacao;
    }
}

