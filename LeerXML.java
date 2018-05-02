package Utilidades;

import Cartas.Carta;
import Cartas.CartaIr;
import Cartas.CartaIrCarcel;
import Cartas.CartaIrEstacion;
import Cartas.CartaIrServicio;
import Cartas.CartaPagar;
import Cartas.CartaPagarCasas;
import Cartas.CartaPagarJugador;
import Cartas.CartaRetroceder;
import Cartas.CartaSalirCarcel;
import Cartas.Mazo;
import Casillas.Casilla;
import Casillas.CasillaCalle;
import Casillas.CasillaComunidad;
import Casillas.CasillaEstacion;
import Casillas.CasillaIrCarcel;
import Casillas.CasillaParking;
import Casillas.CasillaSalida;
import Casillas.CasillaServicio;
import Casillas.CasillaSuerte;
import Casillas.CasillaTasaIngresos;
import Casillas.CasillaTasaLujo;
import java.awt.Color;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.lang.reflect.Field;
import java.util.Vector;


public class LeerXML 
{
    private final String  RUTA_XAML = "datos.xml";
    
    public Carta[] ReadXMLCartasSuerte()
    {
        Vector<Carta> Cartas = new Vector<Carta>();
        try 
        {

            File fXmlFile = new File(RUTA_XAML);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("CartaSuerte");
            
            NodeList CartasSuerte = nList.item(0).getChildNodes();
            
            for (int i = 0; i < CartasSuerte.getLength(); i++) 
            {
                Node nNode = CartasSuerte.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) 
                {
                    Element eElement = (Element) nNode;
                    int indice = Integer.parseInt(eElement.getAttribute("id"));
                    
                    String tag = eElement.getAttribute("type");
                    String texto = eElement.getAttribute("value");
                    int valor = 0;
                    int valor2 = 0;
                    
                    if(!eElement.getAttribute("value2").isEmpty())
                        valor = Integer.parseInt(eElement.getAttribute("value2"));
                    
                    if(!eElement.getAttribute("value3").isEmpty())
                        valor = Integer.parseInt(eElement.getAttribute("value3"));
                                        
                    Cartas.add(indice, SacarCartaSuerte(tag,texto,valor,valor2));
                        
                        

                }
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        
        Carta[] anArray = new Carta[Cartas.size()];
        Cartas.toArray(anArray); 
        return anArray;
    }

    private Carta SacarCartaSuerte(String tag, String Texto,int valor, int valor2) 
    {
        Carta carta = null;
        
        if(tag.equalsIgnoreCase("CartaSalirCarcel"))
            carta = new CartaSalirCarcel(Texto);
        else if(tag.equalsIgnoreCase("CartaIr"))
            carta = new CartaIr(Texto,valor);
        else if(tag.equalsIgnoreCase("CartaPagar"))
            carta = new CartaPagar(Texto,valor);
        else if(tag.equalsIgnoreCase("CartaRetroceder"))
            carta = new CartaRetroceder(valor,Texto);
        else if(tag.equalsIgnoreCase("CartaIrCarcel"))
            carta = new CartaIrCarcel(Texto);
        else if(tag.equalsIgnoreCase("CartaPagarCasas"))
            carta = new CartaPagarCasas(valor,valor2,Texto);
        else if(tag.equalsIgnoreCase("CartaPagarJugador"))
            carta = new CartaPagarJugador(valor,Texto);
        else if(tag.equalsIgnoreCase("CartaIrServicio"))
            carta = new CartaIrServicio(Texto);
        else if(tag.equalsIgnoreCase("CartaIrEstacion"))
            carta = new CartaIrEstacion(Texto);     
        
        return carta;
    }

    public Casilla[] LeerCasillaXML(Mazo MazoCaja, Mazo MazoSuerte) 
    {
       
        Vector<Casilla> Calles = new Vector<Casilla>();
       
        try 
        {

            File fXmlFile = new File(RUTA_XAML);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("Calles");
            
            NodeList CartasSuerte = nList.item(0).getChildNodes();
            
            for (int i = 0; i < CartasSuerte.getLength(); i++) 
            {
                Node nNode = CartasSuerte.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) 
                {
                    Element eElement = (Element) nNode;
                    int indice = Integer.parseInt(eElement.getAttribute("id"));
                    
                    String tag = eElement.getAttribute("type");
                    String texto = eElement.getAttribute("value");
                                        
                    Calles.add(indice, SacarCalle(tag,texto,MazoCaja,MazoSuerte));
                        
                        

                }
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        
        Casilla[] anArray = new Casilla[Calles.size()];
        Calles.toArray(anArray); 
        return anArray;
    }

    private Casilla SacarCalle(String tag, String texto, Mazo MazoCaja, Mazo MazoSuerte) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException 
    {
        Casilla Calle = null;
        String[] Params = null;
        
        if(texto.length() > 0)
            Params = texto.split(",");
        
        if(tag.equalsIgnoreCase("CasillaSalida"))
           Calle = new CasillaSalida();
        else if(tag.equalsIgnoreCase("CasillaTasaIngresos"))
            Calle = new CasillaTasaIngresos();
        else if(tag.equalsIgnoreCase("CasillaEstacion"))
            Calle = new CasillaEstacion(texto);
        else if(tag.equalsIgnoreCase("CasillaComunidad"))
            Calle = new CasillaComunidad(MazoCaja);
        else if(tag.equalsIgnoreCase("CasillaSuerte"))
            Calle = new CasillaSuerte(MazoSuerte);
        else if(tag.equalsIgnoreCase("CasillaTasaLujo"))
            Calle = new CasillaTasaLujo();
        else if(tag.equalsIgnoreCase("CasillaIrCarcel"))
            Calle = new CasillaIrCarcel();
        else if(tag.equalsIgnoreCase("CasillaParking"))
            Calle = new CasillaParking();
        else if(tag.equalsIgnoreCase("CasillaServicio"))
            Calle = new CasillaServicio(Params[0],Integer.parseInt(Params[1].replace(" ", "")));
        else if(tag.equalsIgnoreCase("CasillaCalle"))
        {
           Field field = Color.class.getField(Params[1]);
           Color c = (Color)field.get(null);
           Calle = new CasillaCalle(Params[0],
                                    c,
                                    Integer.parseInt(Params[2].replace(" ", "")),
                                    Integer.parseInt(Params[3].replace(" ", "")),
                                    Integer.parseInt(Params[4].replace(" ", "")),
                                    Integer.parseInt(Params[5].replace(" ", "")),
                                    Integer.parseInt(Params[6].replace(" ", "")),
                                    Integer.parseInt(Params[7].replace(" ", "")),
                                    Integer.parseInt(Params[8].replace(" ", "")),
                                    Integer.parseInt(Params[9].replace(" ", "")),
                                    Integer.parseInt(Params[10].replace(" ", "")));
        } 
         return Calle;
    }
  
}
