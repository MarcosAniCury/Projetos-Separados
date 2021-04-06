package controler;
import model.*;

import java.util.Scanner;
/** 
*   Autor: Marcos Ani Cury Vinagre Silva, Ultima Atualização: 11/11/2020
*
*   A lógica do programa se baseia em criar uma classe que ira ter nela as palavras armazenas em ordem da menor pontuação para a maior pontuação.
*   Para fazer isso eu crie uma clase para armazenar as palavras e outra para fazer a pontuação da palavra, logo só faltou ordenar para concluir essa etapa.
*
*   Apos isso eu utilizei dessa ordenação previa para encurtar o código, logo eu comparei de forma crescente cada palavra do banco com a entrada letra por letra e armazenando as posicoes das letras iguais,
*   apos isso replicava todas as letras basseadas em suas posicoes para comparar com a palavra do banco e assim que ele achava uma palavra identica, ele continua até a palavra que ele estar lendo do banco
*   ter uma pontuacao inferior a palavra do banco (mas caso no meio desse tempo ele ache uma palavra menor ele ira substituir logo que o valor é o mesmo).
*
*   Finalmente na ultima etapa eu utilizei da tática de posição das letras para pegar as letras que existem na palavra escolhida e na entrada, e assim eu isolei as letras que nao existem na entrada e na palavra escolhida
*   
*   Eu escolhi subdividir ao maximo o código para aumentar a compreensão, muito obrigado 
*/
public class Montar_Palavras
{
    public static void main (String [] args)
    {
        //criacao do banco de palavra
        Scanner sc = new Scanner(System.in);
        System.out.print("Digite as letras disponíveis nesta jogada:");
        String entrada = sc.nextLine();
        entrada = entrada.toLowerCase();
        System.out.printf("%s", entrada);
        String palavraescolhida = melhorPalavra(entrada);
        Banco_de_palavras dados = new Banco_de_palavras();
        //verifica se existe um palavra no banco de palavras com as letras da entrada
        if (palavraescolhida.equals(""))
            System.out.print("Nao foi possivel encontrar uma palavra com as letras desejadas");
        else
        {
            System.out.printf("\n%s, palavra de %d pontos\n", palavraescolhida.toUpperCase(), dados.pesquisaSeqPontuacao(palavraescolhida));
            System.out.printf("Sobraram:");
            //verifica se a palavra é igual a entrada
            if (!entrada.equals(palavraescolhida))
            {
                char[] letrasfaltando = letrasFaltantes(entrada, palavraescolhida);
                for (int i = 0;i < letrasfaltando.length;i++)
                    System.out.printf(" %c", letrasfaltando[i]);
            }
        }
        sc.close();
    }

    //clase que ira achar a melhor escolha de palavra
    public static String melhorPalavra(String entrada)
    {
        String retorno = "";
        Banco_de_palavras dados = new Banco_de_palavras();
        int i = dados.getTam() - 1;
        //laco que abrage todas as palavras buscando a melhor
        do
        {
            String testedepalavra = "";
            String palavratestada = dados.getPalavra(i);
            int letraiguais = 0;
            int[] posletra = new int[palavratestada.length() + 1];
            //laco para verificar cada letra da palavra
            for(int j = 0;j < palavratestada.length();j++)
            {
                char letra = palavratestada.charAt(j);
                int k = 0;
                boolean letrajaexiste = false;
                do
                {
                    //verifica se ha mais de uma letra identica na palavra para pegar apos ela
                    if (entrada.charAt(posletra[k]) == letra && posletra[k] != 0)
                    {
                        int teste = entrada.indexOf(letra, posletra[k] + 1);
                        if (teste == -1 )
                            posletra[j + 1] = 0;
                        else
                            posletra[j + 1] = teste; 
                        letrajaexiste = true;
                    }
                    k++;
                }while(k < palavratestada.length() && !letrajaexiste);
                if (!letrajaexiste && entrada.contains(Character.toString(letra)))
                    posletra[j+1] = entrada.indexOf(letra);
            }
            //monta a palavra com as posicoes da letra retirada da palavra testada que combinam com a entrada
            for(int j = 1;j < posletra.length;j++)
                testedepalavra += Character.toString(entrada.charAt(posletra[j]));
            //caso a palavra montada nao seja igual a palavratesta volta a estaca zero
            if (!testedepalavra.equals(palavratestada))
                testedepalavra = "";
            else
                retorno = testedepalavra;
            //substituir por uma palavra do mesmo valor, caso seja menor
            if ((letraiguais == palavratestada.length() && palavratestada.length() <= entrada.length()) && (retorno.equals("") || retorno.length() < letraiguais))
                    retorno = palavratestada;
            i--;
        //caso a pontuacao do retorno seja superior a palavra que esteja lendo nao ha necessidade de continuar o laco
        }while (i >= 0 && dados.getPontuacao(i) >= dados.pesquisaSeqPontuacao(retorno));
        return retorno;
    }

    public static char[] letrasFaltantes(String entrada, String palavra)
    {
        char[] letrasfaltando = new char[entrada.length()];
        int contletrasfaltando = 0;
        //vetor que armazena o posicao dos char de palavras em letras
        int[] posletra = new int[palavra.length() + 1];
        //dessa forma nos preenchemos o vetor com as posicoes da letra
        for(int i = 0;i < palavra.length();i++)
        {
            char letra = palavra.charAt(i);
            int j = 0;
            boolean letrajaexiste = false;
            do
            {
                //verifica se ha mais de uma letra identica na palavra para pegar apos ela
                if (entrada.charAt(posletra[j]) == letra && posletra[j] != 0)
                {
                    int teste = 0;
                    int k = 0;
                    do
                    {
                        if (entrada.indexOf(letra, posletra[j] + 1) != -1 && posletra[k] == teste)
                            teste = entrada.indexOf(letra, posletra[k] + 1);
                        k++;
                    }while(k < posletra.length);
                    posletra[i + 1] = teste;
                    letrajaexiste = true;
                }
                j++;
            }while(j < palavra.length() && !letrajaexiste);
            if (!letrajaexiste)
                posletra[i+1] = entrada.indexOf(letra);
        }
        //laco que vai pegar as letras que nao estao nas posicoes do vetor posletra
        for (int i = 0;i < entrada.length();i++)
        {
            if (!(entrada.charAt(i) >= 'a' && entrada.charAt(i) <= 'z'))
            {
                letrasfaltando[contletrasfaltando] = entrada.charAt(i);
                contletrasfaltando++;
            }
            else
            {
                boolean existeposletra = false;
                int j = 1;
                do
                {
                    if(posletra[j] == i)
                        existeposletra = true;
                    j++;
                }while(j < posletra.length && !existeposletra);
                if(!existeposletra)
                {
                    letrasfaltando[contletrasfaltando] = entrada.charAt(i);
                    contletrasfaltando++;
                }
            }
        }
        return letrasfaltando;
    }
}