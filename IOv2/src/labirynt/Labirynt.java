package labirynt;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * 
 * @author Jaroslaw Jedruszczak
 * @version 1
 * @since 30.01.20176
 *
 */

/**
 * Klasa Labirynt - Glowna klasa programu zawiera wszystkie niezbedne metody poza rozszerzonym JLabelem z klasy "OknoGry"
 */
public class Labirynt implements ActionListener,MouseMotionListener,MouseListener {

	/**
	 *  Obiekt tej klasy uzywany przez metode Main oraz przez klase OknoGry
	 */
	public static Labirynt gra;
	
	/**
	 * Label PozycjaMyszy wykazuje Pozycje myszy :)
	 */
	public static JLabel PozycjaMyszy;
	/**
	 * Labele Odlegosc wykazuja odleglosc w stalych jednostkach od Scian
	 */
	public static JLabel Odleglosci, 
				 		 OdlegloscNW,     
				 		 OdlegloscN,            
				 		 OdlegloscNE;
	/**
	 * Label okreslajacy kierunek postaci
	 */
	public static JLabel KierunekPostaciLabel;
	
	public static JLabel KlasyfikacjaOdleglosci;
	/**
	 * Publiczna deklaracja Przyciskow uzywanych przez calą klase
	 */
	public static JButton DodajGraczaB,
	 					  UsunScianyB,
						  UsunGraczaB;
	/**
 	* Generator przypadkowych liczb 
 	*/
	public static Random rand =new Random();
	/**
	 * Tablica zawierajaca Sciany
	 */
	public ArrayList<Rectangle> Sciany;
	/**
	 * Obiekt klasy rozszerzajacej JLabel ktory uzywany jest do
	 */
	public OknoGry oknogry = new OknoGry();
	/**
	 *  Rectangle Postac to obiekt utworzony specjalnie dla Postaci poruszajacej sie po przestrzeni
	 */
	public Rectangle Postac;
	/**
	 * JSlider odpowiedzialny zazmienianie szybkosci programu
	 */
	public JSlider SliderTimera;
	/**
	 * Timer ktory odpowiedzialny jest za uruchamianie metody ActionListnerer
	 */
	public Timer timer;
	
	/**
	 * Zadeklarowany rozmiar Okna gry
	 */
	public  int WYSOKOSC = 760, SZEROKOSC = 985;
	/**
	 * Zadeklarowana pozycja Postaci
	 */
			int PozycjaX, PozycjaY;
	/**
	 * Zadeklarowane wspulzedne dla poczatku rysowanych scian
	 */
			int PoczatekScianyX, PoczatekScianyY;
	/**
	 * Zadeklarowane dlugosci sensorow		
	 */
			int  DlugoscNW, DlugoscN, DlugoscNE;
	/**
	 * Zadeklarowany Kierunek postaci z inicjalizacja wartoscia 17 dla braku ruchu
	 */
			static int KierunekPostaci = 17;
	/**
	 * Zadeklarowane Kierunki sensorow
	 */
			int KierunekNW,KierunekN,KierunekNE;
	/**
	 * Zadeklarowana czestotliwosc pracy programu okreslajaca jednoczesnie szybkosc postaci
	 */
			int Czestotliwosc;
	/**
	 * Zadeklarowane zmienne okreslajace pozycje z inicjalizacja wspulzendnej wyrzucajacej Postac poza widok		
	 */
	public double xPostaci=-25,
				  yPostaci=-25;
	public double krotkiD, sredniD, dalekiD;
	/**
	 * Zadeklarowane wspolzedne koncow sensorow
	 */
	public double xKoncaNW,
				  yKoncaNW,
				  xKoncaN,
				  yKoncaN,
				  xKoncaNE,
				  yKoncaNE;
	
	/**
	 *  Zadeklarowana zmienna okreslajaca przeskok postaci miedzy pikselami 
	 */
    static double Predkosc = 1.0;
	/**
	 * Ta zminnna kiedy ma wartosc "true" pozwala na tworzenie "scian" poprzez klikniecie, przeciagniecie myszy, i upuszczenie
	 */
	public boolean CzyRysujeSciany;
	/**
	 * Ta zmienna kiedy ma wartosc "true" pozwala za pomoca klikniecia myszy ustawic pozycje gracza
	 */
		   boolean CzyRysujePostac;
	/**
	 * Te zmienne odpowiedzialne sa za zwracanie dlugosci lini sensora
	 */
		   boolean RysowanieSensoraN = false,
				   RysowanieSensoraNW = false,
				   RysowanieSensoraNE = false;
		   static boolean RysowanieSensorow = false;
	/**
	 * Zmienna decyduje o rysowaniu sensorow
	 */
		   boolean Start = false;
		   	
			
	/**
	 * Konstruktor Klasy
	 * odpowiedzialny za budowanie calego interfejsu, zawiera rowniez ActionListenery Buttonow
	 */
	public Labirynt()
	{
		JFrame okno = new JFrame("Labirynt");
		okno.setSize(1200, 800);
		okno.setLocationRelativeTo(null);
		okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		okno.setResizable(false);			
		okno.setLayout(null);
		okno.setVisible(true);
		
		JPanel panelOpcji = new JPanel();
		okno.add(panelOpcji);
		panelOpcji.setBounds(5, 5, 190, 760);
		panelOpcji.setBackground(Color.gray);
		panelOpcji.setLayout(null);
		
		JButton DodajScianyB = new JButton("Dodaj Sciany");
		DodajScianyB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				DodajSciane(0, 0, 0, 760);
				DodajSciane(0,0,985,0);		
				DodajSciane(985,760,985,0);
				DodajSciane(985,760,0,760);
				Start = true;
				DodajScianyB.setEnabled(false);
			}
		});
		panelOpcji.add(DodajScianyB);
		DodajScianyB.setBounds(5, 5, 125, 50);
		
		JButton RysujScianeBwlacz = new JButton("Wlacz rysowanie");
		JButton RysujScianeBwylacz = new JButton(" < ");
		CzyRysujeSciany = false;
		RysujScianeBwlacz.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				CzyRysujeSciany = true;
				RysujScianeBwlacz.setEnabled(false);
				RysujScianeBwylacz.setEnabled(true);
			}
		});
		panelOpcji.add(RysujScianeBwlacz);
		RysujScianeBwlacz.setBounds(5, 60, 125, 50);
		
		
		CzyRysujeSciany = false;
		RysujScianeBwylacz.setEnabled(false);
		RysujScianeBwylacz.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				CzyRysujeSciany = false;
				RysujScianeBwylacz.setEnabled(false);
				RysujScianeBwlacz.setEnabled(true);				
			}
		});
		panelOpcji.add(RysujScianeBwylacz);
		RysujScianeBwylacz.setBounds(130, 60, 50, 50);
		
		UsunScianyB = new JButton("Wyczysc");
		UsunScianyB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Sciany.clear();
				CzyRysujeSciany = false;
				DodajScianyB.setEnabled(true);
				RysujScianeBwylacz.setEnabled(false);
				RysujScianeBwlacz.setEnabled(true);	
			}
		});
		panelOpcji.add(UsunScianyB);
		UsunScianyB.setBounds(5, 115, 125, 50);
		
		DodajGraczaB = new JButton("Dodaj Postac");
		CzyRysujePostac = false;
		DodajGraczaB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				UsunScianyB.setEnabled(false);
				CzyRysujePostac = true;
				CzyRysujeSciany = false;
				RysujScianeBwylacz.setEnabled(false);
				RysujScianeBwlacz.setEnabled(true);	
				DodajGraczaB.setEnabled(false);
				UsunGraczaB.setEnabled(true);
			}
		});
		panelOpcji.add(DodajGraczaB);
		DodajGraczaB.setBounds(5, 170, 125, 50);
		
		UsunGraczaB = new JButton("Usun Postac");
		UsunGraczaB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				UsunScianyB.setEnabled(true);
				DodajGraczaB.setEnabled(true);
				xPostaci = -24.0;
				yPostaci = -24.0;
				
			}
		});
		panelOpcji.add(UsunGraczaB);
		UsunGraczaB.setBounds(5, 230, 125, 50);
		
		JLabel SliderTimeraLabel = new JLabel("Szybkosc programu");
		panelOpcji.add(SliderTimeraLabel);
		SliderTimeraLabel.setBounds(5, 460, 165, 25);
		
		SliderTimera = new JSlider();
		panelOpcji.add(SliderTimera);
		SliderTimera.setBounds(5, 480, 165, 40);
		SliderTimera.setBackground(Color.GRAY);
		SliderTimera.setValue(20);
		SliderTimera.setMinimum(5);
		SliderTimera.setMaximum(50);
		SliderTimera.setMajorTickSpacing(5);
		SliderTimera.setPaintTicks(true);
		SliderTimera.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				Czestotliwosc = SliderTimera.getValue();
				timer.setDelay(Czestotliwosc);
			}
		});
		
		KierunekPostaciLabel = new JLabel("Kierunek");
		panelOpcji.add(KierunekPostaciLabel);
		KierunekPostaciLabel.setBounds(5, 520, 165, 25); 
		
		Odleglosci = new JLabel("Sensory"); 
		panelOpcji.add(Odleglosci);
		Odleglosci.setBounds(5, 550, 165, 25);
		
		KlasyfikacjaOdleglosci = new JLabel("Klasyfikacja odleglosci");
		panelOpcji.add(KlasyfikacjaOdleglosci);
		KlasyfikacjaOdleglosci.setBounds(5, 690, 165, 25);
		
        OdlegloscNW = new JLabel("Dystans NW =");   
        panelOpcji.add(OdlegloscNW);   
        OdlegloscNW.setBounds(5, 590, 165, 25);
        
        OdlegloscN = new JLabel("Dystans N ="); 
        panelOpcji.add(OdlegloscN);
        OdlegloscN.setBounds(5, 620, 165, 25);
        
        OdlegloscNE = new JLabel("Dystans NE =");      
        panelOpcji.add(OdlegloscNE);
        OdlegloscNE.setBounds(5, 650, 165, 25);
        
        
		
		PozycjaMyszy = new JLabel("Pozycja Myszy");
		panelOpcji.add(PozycjaMyszy);
		PozycjaMyszy.setBounds(5, 730, 165, 25);
		
		okno.add(oknogry);
		Sciany = new ArrayList<Rectangle>();
		Postac = new Rectangle((int)xPostaci,(int)yPostaci,12,12);
		oknogry.setBounds(205, 5, SZEROKOSC, WYSOKOSC);
		oknogry.addMouseMotionListener(this);
		oknogry.addMouseListener(this); 
		
		
		Czestotliwosc = SliderTimera.getValue();
		timer = new Timer(Czestotliwosc, this);
		timer.start();
	}
	
	/**
	 *  Metoda ta rysuje sciany zlozone z kwadratow. Kwadraty maja wielkosc od 5 do 10. Poszczegulne bloki kodu odpoiedzialne sa za rysowanie sciany w roznych kierunkach
	 */
    public void DodajSciane(int xPoczatkuSciany, int yPoczatkuSciany, int xKoncaSciany, int yKoncaSciany)
	{
		// zmienna AntyCrash pozwala na wyjscie z pentli kiedy tworzona sciana nie miesci sie w przewidzianych ponizej przypadkach
		int AntyCrash = 0;
		boolean czyKontynuowac = true;
		int WielkoscKwadrata;
		while(czyKontynuowac)
		{
			AntyCrash++;
			WielkoscKwadrata = rand.nextInt(5)+5;
			// XXX Pierwszy blok rysowania sciany (NORMALNY czyli x i y sa inkrementowane lub XX sa stale)
			if(xPoczatkuSciany <= xKoncaSciany)
				{
					if(xPoczatkuSciany == xKoncaSciany && yPoczatkuSciany < yKoncaSciany)
						{
							if ( yPoczatkuSciany + 12 >= yKoncaSciany)
							{
								WielkoscKwadrata = yKoncaSciany - yPoczatkuSciany;
								czyKontynuowac = false;
							}
							
							Sciany.add(new Rectangle(xPoczatkuSciany, yPoczatkuSciany, WielkoscKwadrata, WielkoscKwadrata));
							yPoczatkuSciany+= WielkoscKwadrata;
						}
						else
						if(xPoczatkuSciany == xKoncaSciany && yPoczatkuSciany > yKoncaSciany)
						{
							if (yPoczatkuSciany - 12 <= yKoncaSciany)
							{
								WielkoscKwadrata = yPoczatkuSciany - yKoncaSciany;
								czyKontynuowac = false;
							}
							Sciany.add(new Rectangle(xPoczatkuSciany-WielkoscKwadrata, yPoczatkuSciany-WielkoscKwadrata, WielkoscKwadrata, WielkoscKwadrata));
							yPoczatkuSciany-= WielkoscKwadrata;
						}
						else
					if(yPoczatkuSciany == yKoncaSciany)
						{
							if (xPoczatkuSciany + 12 >= xKoncaSciany)
								{
									WielkoscKwadrata = xKoncaSciany - xPoczatkuSciany;
									czyKontynuowac = false;
								}
								
							Sciany.add(new Rectangle(xPoczatkuSciany, yPoczatkuSciany, WielkoscKwadrata, WielkoscKwadrata));
							xPoczatkuSciany+= WielkoscKwadrata;
						}
						else
					//Sciny pod katem
					// przekatne od NW do SE inkrementowane	
					if( xPoczatkuSciany<xKoncaSciany && yPoczatkuSciany < yKoncaSciany)
					{
						if (((xPoczatkuSciany + 12) >= xKoncaSciany) && ((yPoczatkuSciany + 12) >= yKoncaSciany))
						{
							if ( Math.abs(xPoczatkuSciany-xKoncaSciany) < Math.abs(yPoczatkuSciany-yKoncaSciany))
							{
								WielkoscKwadrata = Math.abs(yPoczatkuSciany-yKoncaSciany);
							}
							else
							{
								WielkoscKwadrata = Math.abs(xPoczatkuSciany-xKoncaSciany);
							}
							Sciany.add(new Rectangle(xPoczatkuSciany, yPoczatkuSciany, WielkoscKwadrata, WielkoscKwadrata));
							czyKontynuowac = false;
							break;
						}
						boolean ObliczanieNWD = true;
						int Szerokosc = Math.abs(xPoczatkuSciany-xKoncaSciany), Wysokosc = Math.abs(yPoczatkuSciany-yKoncaSciany);
						while (ObliczanieNWD)
						{	
							Szerokosc = Szerokosc/2;
							Wysokosc = Wysokosc/2;	
							if ( Szerokosc < 15 && Wysokosc < 10) ObliczanieNWD = false;	
						}
						WielkoscKwadrata =(int)Math.sqrt(Szerokosc*Szerokosc+Wysokosc*Wysokosc);
						Sciany.add(new Rectangle(xPoczatkuSciany, yPoczatkuSciany, WielkoscKwadrata, WielkoscKwadrata));
						xPoczatkuSciany=xPoczatkuSciany+Szerokosc;
						yPoczatkuSciany=yPoczatkuSciany+Wysokosc;
					}
					else
					// przekatne od SW do NE inkrementowane		
					if( xPoczatkuSciany<xKoncaSciany && yPoczatkuSciany > yKoncaSciany)
					{
						if ((xPoczatkuSciany + 12 >= xKoncaSciany) && (yKoncaSciany + 12 >= yPoczatkuSciany))
						{
							if ( Math.abs(xPoczatkuSciany-xKoncaSciany) < Math.abs(yKoncaSciany-yPoczatkuSciany))
							{
								WielkoscKwadrata = Math.abs(yKoncaSciany-yPoczatkuSciany);
							}
							else
							{
								WielkoscKwadrata = Math.abs(xPoczatkuSciany-xKoncaSciany);
							}
							Sciany.add(new Rectangle(xPoczatkuSciany, yPoczatkuSciany, WielkoscKwadrata, WielkoscKwadrata));
							czyKontynuowac = false;
							break;
						}
						boolean ObliczanieNWD = true;
						int Szerokosc = Math.abs(xPoczatkuSciany-xKoncaSciany), Wysokosc = Math.abs(yKoncaSciany-yPoczatkuSciany);
						while (ObliczanieNWD)
						{	
							Szerokosc = Szerokosc/2;
							Wysokosc = Wysokosc/2;	
							if ( Szerokosc < 15 && Wysokosc < 10) ObliczanieNWD = false;
						}
						WielkoscKwadrata =(int)Math.sqrt(Szerokosc*Szerokosc+Wysokosc*Wysokosc);
						Sciany.add(new Rectangle(xPoczatkuSciany, yPoczatkuSciany, WielkoscKwadrata, WielkoscKwadrata));
						xPoczatkuSciany=xPoczatkuSciany+Szerokosc;
						yPoczatkuSciany=yPoczatkuSciany-Wysokosc;
					}
				}
			else
			// XXX Druga wersja pierwszego bloku (NORMALNY ODWROCONY czyli x i y sa dekrementowane)
				{
					if(xPoczatkuSciany >= xKoncaSciany)
					{
					if(yPoczatkuSciany == yKoncaSciany)
						{			
							if (xPoczatkuSciany - 12 <= xKoncaSciany)
								{
									WielkoscKwadrata =  xPoczatkuSciany - xKoncaSciany ;
									czyKontynuowac = false;
								}
							Sciany.add(new Rectangle(xPoczatkuSciany-WielkoscKwadrata, yPoczatkuSciany-WielkoscKwadrata, WielkoscKwadrata, WielkoscKwadrata));
							xPoczatkuSciany-= WielkoscKwadrata;
						}else
					//Przekatne odwrotne
					//od NE do SW
					if(yPoczatkuSciany < yKoncaSciany)
					{
						if ((xPoczatkuSciany - 12 <= xKoncaSciany) && (yPoczatkuSciany + 12 >= yKoncaSciany))
						{
							if ( Math.abs(xPoczatkuSciany-xKoncaSciany) < Math.abs(yPoczatkuSciany-yKoncaSciany))
							{
								WielkoscKwadrata = Math.abs(yPoczatkuSciany-yKoncaSciany);
							}
							else
							{
								WielkoscKwadrata = Math.abs(xPoczatkuSciany-xKoncaSciany);
							}
							Sciany.add(new Rectangle(xPoczatkuSciany, yPoczatkuSciany, WielkoscKwadrata, WielkoscKwadrata));
							czyKontynuowac = false;
							break;
						}
						boolean ObliczanieNWD = true;
						int Szerokosc = Math.abs(xPoczatkuSciany-xKoncaSciany), Wysokosc = Math.abs(yPoczatkuSciany-yKoncaSciany);
						while (ObliczanieNWD)
						{	
							Szerokosc = Szerokosc/2;
							Wysokosc = Wysokosc/2;	
							if ( Szerokosc < 15 && Wysokosc < 10) ObliczanieNWD = false;
						}
						xPoczatkuSciany=xPoczatkuSciany-Szerokosc;
						yPoczatkuSciany=yPoczatkuSciany+Wysokosc;
						WielkoscKwadrata =(int)Math.sqrt(Szerokosc*Szerokosc+Wysokosc*Wysokosc);
						Sciany.add(new Rectangle(xPoczatkuSciany, yPoczatkuSciany, WielkoscKwadrata, WielkoscKwadrata));
					}
					else	
					//od SE do NW
					if(yPoczatkuSciany > yKoncaSciany)
						{
							if ((xPoczatkuSciany - 12 <= xKoncaSciany) && (yPoczatkuSciany - 12 <= yKoncaSciany))
							{
								if ( Math.abs(xPoczatkuSciany-xKoncaSciany) < Math.abs(yPoczatkuSciany-yKoncaSciany))
								{
									WielkoscKwadrata = Math.abs(yPoczatkuSciany-yKoncaSciany);
								}
								else
								{
									WielkoscKwadrata = Math.abs(xPoczatkuSciany-xKoncaSciany);
								}
								Sciany.add(new Rectangle(xPoczatkuSciany, yPoczatkuSciany, WielkoscKwadrata, WielkoscKwadrata));
								czyKontynuowac = false;
								break;
							}
							boolean ObliczanieNWD = true;
							int Szerokosc = Math.abs(xKoncaSciany-xPoczatkuSciany), Wysokosc = Math.abs(yKoncaSciany-yPoczatkuSciany);
							while (ObliczanieNWD)
							{	
								Szerokosc = Szerokosc/2;
								Wysokosc = Wysokosc/2;	
								if ( Szerokosc < 15 && Wysokosc < 10) ObliczanieNWD = false;
							}
							WielkoscKwadrata =(int)Math.sqrt(Szerokosc*Szerokosc+Wysokosc*Wysokosc);
							xPoczatkuSciany=xPoczatkuSciany-Szerokosc;
							yPoczatkuSciany=yPoczatkuSciany-Wysokosc;
							Sciany.add(new Rectangle(xPoczatkuSciany, yPoczatkuSciany, WielkoscKwadrata, WielkoscKwadrata));
						}
					}			
				} 
			// Porownywana liczba musi byc duza poniewaz inne linie musza sie w tylu krokach dokonczyc
			if (AntyCrash == 500) {System.out.println("Blad: nie ma takiej sciany");czyKontynuowac=false;}
		}
	}
	/**
	 *  Metoda odpowiedzialna za rysowanie obiektow wewnacz obiektu klasy OknoGry
	 */
	public void repaint(Graphics g) {
		g.setColor(Color.black);
		
		malujSensor(g,(int)xKoncaNW,(int)yKoncaNW);
		malujSensor(g,(int)xKoncaNE,(int)yKoncaNE);
		g.setColor(Color.red);
		malujSensor(g,(int)xKoncaN,(int)yKoncaN);
		for (Rectangle BlokSciany : Sciany)
		{
			malujSciane(g, BlokSciany);
			
		}
		g.setColor(new Color(200,0,0));
		g.fillRect(Postac.x, Postac.y, Postac.width, Postac.height);
	}
	/**
	 * Metoda rysuje pojedyncze kwadraty uzywane podczas rysowania sciany
	 * @param blokSciany zmienna opisujaca 1 kwadrat wewnacz tablicy kwadratow
	 */
	private void malujSciane(Graphics g, Rectangle blokSciany) {
		g.setColor(new Color (0, rand.nextInt(100)+120, 0));
		g.fillRect(blokSciany.x, blokSciany.y, blokSciany.width, blokSciany.height);
	}
	/**
	 * Metoda rysujaca Sensory
	 * @param xKoncaSensora Wspolzedna X konca sensora
	 * @param yKoncaSensora Wspolzedna Y konca sensora
	 */
	private void malujSensor(Graphics g, int xKoncaSensora, int yKoncaSensora) {
		
		g.drawLine((int)xPostaci+6, (int)yPostaci+6, xKoncaSensora+(int)xPostaci+6, yKoncaSensora+(int)yPostaci+6);
	}
	/**
	 * 	Main wywoluje obiekt tej klasy
	 */
	public static void main(String[] args) {
	
		gra = new Labirynt();
	}
	/**
	 *  Metoda okreslajaca wspolzedna X, wywolywana na zecz Sensora i Postaci
	 * @param obliczanieX aktualna wspolzedna x
	 * @param obliczanieY aktualna wspolzedna y
	 * @param Kierunek kierunek w jakim ma poruszac sie punkt
	 * @return zwraca wspolzedna x po przesunieciu
	 */
	public static double okreslanieKierunkuX(double obliczanieX,double obliczanieY,int Kierunek)
	{
		switch(Kierunek)
		{
			case(0): //N
				if (RysowanieSensorow)
				{
					obliczanieY-=1;
				}
				else
				{
				obliczanieY-=Predkosc;
				KierunekPostaciLabel.setText("Kierunek: N");
				}
				break;
			case(1): //NNW
				if (RysowanieSensorow)
				{
					obliczanieY-=1/1.2;
					obliczanieX+=0.5/1.2;
				}
				else
				{
				obliczanieY-=Predkosc/1.2;
				obliczanieX+=Predkosc/2/1.2;
				KierunekPostaciLabel.setText("Kierunek: NNW");
				}
				break;
			case(2): //NW
				if (RysowanieSensorow)
				{
					obliczanieY-=1/1.4;
					obliczanieX+=1/1.4;
				}
				else
				{
				obliczanieY-=Predkosc/1.4;
				obliczanieX+=Predkosc/1.4;
				KierunekPostaciLabel.setText("Kierunek: NW");
				}
				break;
			case(3): //NWW
				if (RysowanieSensorow)
				{
					obliczanieY-=0.5/1.2;
					obliczanieX+=1/1.2;
				}
				else
				{
				obliczanieY-=Predkosc/2/1.2;
				obliczanieX+=Predkosc/1.2;
				KierunekPostaciLabel.setText("Kierunek: NWW");
				}
				break;
			case(4): //W			
				if (RysowanieSensorow)
				{
					obliczanieX+=1;
				}
				else
				{
				obliczanieX+=Predkosc;
				KierunekPostaciLabel.setText("Kierunek: W");
				}
				break;
			case(5): //SWW
				if (RysowanieSensorow)
				{
					obliczanieX+=1/1.2;
					obliczanieY+=0.5/1.2;
				}
				else
				{
				obliczanieX+=Predkosc/1.2;
				obliczanieY+=Predkosc/2/1.2;
				KierunekPostaciLabel.setText("Kierunek: SWW");
				}
				break;
			case(6): //SW
				if (RysowanieSensorow)
				{
					obliczanieX+=1/1.4;
					obliczanieY+=1/1.4;
				}
				else
				{
				obliczanieX+=Predkosc/1.4;
				obliczanieY+=Predkosc/1.4;
				KierunekPostaciLabel.setText("Kierunek: SW");
				}
				break;
			case(7): //SSW
				if (RysowanieSensorow)
				{
					obliczanieX+=0.5/1.2;
					obliczanieY+=1/1.2;
				}
				else
				{
				obliczanieX+=Predkosc/2/1.2;
				obliczanieY+=Predkosc/1.2;
				KierunekPostaciLabel.setText("Kierunek: SSW");
				}
				break;
			case(8): //S		
				if (RysowanieSensorow)
				{
					obliczanieY+=1;
				}
				else
				{
				obliczanieY+=Predkosc;
				KierunekPostaciLabel.setText("Kierunek: S");
				}
				break;
			case(9): //SSE
				if (RysowanieSensorow)
				{
					obliczanieY+=1/1.2;
					obliczanieX-=0.5/1.2;
				}
				else
				{
				obliczanieY+=Predkosc/1.2;
				obliczanieX-=Predkosc/2/1.2;
				KierunekPostaciLabel.setText("Kierunek: SSE");	
				}
				break;
			case(10): //SE
				if (RysowanieSensorow)
				{
					obliczanieY+=1/1.4;
					obliczanieX-=1/1.4;
				}
				else
				{
				obliczanieY+=Predkosc/1.4;
				obliczanieX-=Predkosc/1.4;
				KierunekPostaciLabel.setText("Kierunek: SE");
				}
				break;
			case(11): //SEE
				if (RysowanieSensorow)
				{
					obliczanieY+=0.5/1.2;
					obliczanieX-=1/1.2;
				}
				else
				{
				obliczanieY+=Predkosc/2/1.2;
				obliczanieX-=Predkosc/1.2;
				KierunekPostaciLabel.setText("Kierunek: SEE");
				}
				break;
			case(12): //E		
				if (RysowanieSensorow)
				{
					obliczanieX-=1;
				}
				else
				{
				obliczanieX-=Predkosc;
				KierunekPostaciLabel.setText("Kierunek: E");
				}
				break;
			case(13): //NEE
				if (RysowanieSensorow)
				{
					obliczanieX-=1/1.2;
					obliczanieY-=0.5/1.2;
				}
				else
				{
				obliczanieX-=Predkosc/1.2;
				obliczanieY-=Predkosc/2/1.2;
				KierunekPostaciLabel.setText("Kierunek: NEE");
				}
				break;
			case(14): //NE
				if (RysowanieSensorow)
				{
					obliczanieX-=1/1.4;
					obliczanieY-=1/1.4;
				}
				else
				{
				obliczanieX-=Predkosc/1.4;
				obliczanieY-=Predkosc/1.4;
				KierunekPostaciLabel.setText("Kierunek: NE");
				}
				break;
			case(15): //NNE
				if (RysowanieSensorow)
				{
					obliczanieX-=0.5/1.2;
					obliczanieY-=1/1.2;
				}
				else
				{
				obliczanieX-=Predkosc/2/1.2;
				obliczanieY-=Predkosc/1.2;
				KierunekPostaciLabel.setText("Kierunek: NNE");
				}
				break;
		}
		return  obliczanieX;
	}
	/**
	 *  Metoda okreslajaca wspolzedna Y, wywolywana na zecz Sensora i Postaci
	 * @param obliczanieX aktualna wspolzedna x
	 * @param obliczanieY aktualna wspolzedna y
	 * @param Kierunek kierunek w jakim ma poruszac sie punkt
	 * @return zwraca wspolzedna y po przesunieciu
	 */
	public static double okreslanieKierunkuY(double obliczanieX,double obliczanieY,int Kierunek)
	{
		switch(Kierunek)
		{
			case(0): //N
				if (RysowanieSensorow)
				{
					obliczanieY-=1;
				}
				else
				{
				obliczanieY-=Predkosc;
				KierunekPostaciLabel.setText("Kierunek: N");
				}
				break;
			case(1): //NNW
				if (RysowanieSensorow)
				{
					obliczanieY-=1/1.2;
					obliczanieX+=0.5/1.2;
				}
				else
				{
				obliczanieY-=Predkosc/1.2;
				obliczanieX+=Predkosc/2/1.2;
				KierunekPostaciLabel.setText("Kierunek: NNW");
				}
				break;
			case(2): //NW
				if (RysowanieSensorow)
				{
					obliczanieY-=1/1.4;
					obliczanieX+=1/1.4;
				}
				else
				{
				obliczanieY-=Predkosc/1.4;
				obliczanieX+=Predkosc/1.4;
				KierunekPostaciLabel.setText("Kierunek: NW");
				}
				break;
			case(3): //NWW
				if (RysowanieSensorow)
				{
					obliczanieY-=0.5/1.2;
					obliczanieX+=1/1.2;
				}
				else
				{
				obliczanieY-=Predkosc/2/1.2;
				obliczanieX+=Predkosc/1.2;
				KierunekPostaciLabel.setText("Kierunek: NWW");
				}
				break;
			case(4): //W	
				if (RysowanieSensorow)
				{
					obliczanieX+=1;
				}
				else
				{
				obliczanieX+=Predkosc;
				KierunekPostaciLabel.setText("Kierunek: W");
				}
				break;
			case(5): //SWW
				if (RysowanieSensorow)
				{
					obliczanieX+=1/1.2;
					obliczanieY+=0.5/1.2;
				}
				else
				{
				obliczanieX+=Predkosc/1.2;
				obliczanieY+=Predkosc/2/1.2;
				KierunekPostaciLabel.setText("Kierunek: SWW");
				}
				break;
			case(6): //SW
				if (RysowanieSensorow)
				{
					obliczanieX+=1/1.4;
					obliczanieY+=1/1.4;
				}
				else
				{
				obliczanieX+=Predkosc/1.4;
				obliczanieY+=Predkosc/1.4;
				KierunekPostaciLabel.setText("Kierunek: SW");
				}
				break;
			case(7): //SSW
				if (RysowanieSensorow)
				{
					obliczanieX+=0.5/1.2;
					obliczanieY+=1/1.2;
				}
				else
				{
				obliczanieX+=Predkosc/2/1.2;
				obliczanieY+=Predkosc/1.2;
				KierunekPostaciLabel.setText("Kierunek: SSW");
				}
				break;
			case(8): //S		
				if (RysowanieSensorow)
				{
					obliczanieY+=1;
				}
				else
				{
				obliczanieY+=Predkosc;
				KierunekPostaciLabel.setText("Kierunek: S");
				}
				break;
			case(9): //SSE
				if (RysowanieSensorow)
				{
					obliczanieY+=1/1.2;
					obliczanieX-=0.5/1.2;
				}
				else
				{
				obliczanieY+=Predkosc/1.2;
				obliczanieX-=Predkosc/2/1.2;
				KierunekPostaciLabel.setText("Kierunek: SSE");
				}
				break;
			case(10): //SE
				if (RysowanieSensorow)
				{
					obliczanieY+=1/1.4;
					obliczanieX-=1/1.4;
				}
				else
				{
				obliczanieY+=Predkosc/1.4;
				obliczanieX-=Predkosc/1.4;
				KierunekPostaciLabel.setText("Kierunek: SE");
				}
				break;
			case(11): //SEE
				if (RysowanieSensorow)
				{
					obliczanieY+=0.5/1.2;
					obliczanieX-=1/1.2;
				}
				else
				{
				obliczanieY+=Predkosc/2/1.2;
				obliczanieX-=Predkosc/1.2;
				KierunekPostaciLabel.setText("Kierunek: SEE");
				}
				break;
			case(12): //E	
				if (RysowanieSensorow)
				{
					obliczanieX-=1;
				}
				else
				{
				obliczanieX-=Predkosc;
				KierunekPostaciLabel.setText("Kierunek: E");
				}
				break;
			case(13): //NEE
				if (RysowanieSensorow)
				{
					obliczanieX-=1/1.2;
					obliczanieY-=0.5/1.2;
				}
				else
				{
				obliczanieX-=Predkosc/1.2;
				obliczanieY-=Predkosc/2/1.2;
				KierunekPostaciLabel.setText("Kierunek: NEE");
				}
				break;
			case(14): //NE
				if (RysowanieSensorow)
				{
					obliczanieX-=1/1.4;
					obliczanieY-=1/1.4;
				}
				else
				{
				obliczanieX-=Predkosc/1.4;
				obliczanieY-=Predkosc/1.4;
				KierunekPostaciLabel.setText("Kierunek: NE");
				}
				break;
			case(15): //NNE
				if (RysowanieSensorow)
				{
					obliczanieX-=0.5/1.2;
					obliczanieY-=1/1.2;
				}
				else
				{
				obliczanieX-=Predkosc/2/1.2;
				obliczanieY-=Predkosc/1.2;
				KierunekPostaciLabel.setText("Kierunek: NNE");
				}
				break;
		}
		return  obliczanieY;
	}
	@Override
	/**
	 * Metoda wywolywana przez Timer
	 * kazde jej wywolanie powoduje przesuniecie Postaci, Sensorow i malowanie wszystkich elementow
	 */
	public void actionPerformed(ActionEvent e) {
		xKoncaN = 0;
		yKoncaN = 0;
		xKoncaNE = 0;
		yKoncaNE = 0;
		xKoncaNW = 0;
		yKoncaNW = 0;
		DlugoscN = 0;
		DlugoscNW = 0;
		DlugoscNE = 0;
		krotkiD = 0;
		sredniD = 0;
		dalekiD = 0;
		RysowanieSensoraN = true;
		RysowanieSensoraNE = true;
		RysowanieSensoraNW = true;
		xPostaci = okreslanieKierunkuX(xPostaci,yPostaci,KierunekPostaci);
		yPostaci = okreslanieKierunkuY(xPostaci,yPostaci,KierunekPostaci);
		Postac.setLocation((int)xPostaci, (int)yPostaci);
		KierunekN = KierunekPostaci;
		KierunekNE = KierunekPostaci+2;
		if (KierunekNE > 15) KierunekNE = KierunekNE -16;
		KierunekNW = KierunekPostaci-2;
		if (KierunekNW < 0 ) KierunekNW = KierunekNW +16;
		
		
		if((xPostaci >0)&&(yPostaci >0)&&Start)
		{	
			RysowanieSensorow = true;
			while(RysowanieSensoraN)
				{
					KierunekN = KierunekPostaci;
					yKoncaN = okreslanieKierunkuY(xKoncaN,yKoncaN,KierunekN);
					xKoncaN = okreslanieKierunkuX(xKoncaN,yKoncaN,KierunekN);
					for (Rectangle BlokSciany : Sciany )
					{
						if(BlokSciany.intersectsLine((int)xPostaci+6, (int)yPostaci+6, xKoncaN+(int)xPostaci+6, yKoncaN+(int)yPostaci+6)){
							RysowanieSensoraN = false;
						}
					}
					DlugoscN++;
					if(DlugoscN == 75) RysowanieSensoraN = false;
				}
			while(RysowanieSensoraNE)
				{
					yKoncaNE = okreslanieKierunkuY(xKoncaNE,yKoncaNE,KierunekNE);
					xKoncaNE = okreslanieKierunkuX(xKoncaNE,yKoncaNE,KierunekNE);
					for (Rectangle BlokSciany : Sciany )
					{
						if(BlokSciany.intersectsLine((int)xPostaci+6, (int)yPostaci+6, xKoncaNE+(int)xPostaci+6, yKoncaNE+(int)yPostaci+6)){
							RysowanieSensoraNE = false;
						}
					}
					DlugoscNE++;
					if(DlugoscNE == 150) RysowanieSensoraNE = false;
				}
			while(RysowanieSensoraNW)
				{
					yKoncaNW = okreslanieKierunkuY(xKoncaNW,yKoncaNW,KierunekNW);
					xKoncaNW = okreslanieKierunkuX(xKoncaNW,yKoncaNW,KierunekNW);
					for (Rectangle BlokSciany : Sciany )
					{
						if(BlokSciany.intersectsLine((int)xPostaci+6, (int)yPostaci+6, xKoncaNW+(int)xPostaci+6, yKoncaNW+(int)yPostaci+6)){
							RysowanieSensoraNW = false;
						}
					}
					DlugoscNW++;
					if(DlugoscNW == 150) RysowanieSensoraNW = false;
				}
			RysowanieSensorow = false;
		}
		OdlegloscN.setText("Dystans N = "+DlugoscN);
		OdlegloscNE.setText("Dystans NE = "+DlugoscNE);
		OdlegloscNW.setText("Dystans NW = "+DlugoscNW);
		
		MetodaKlasyfikacjiIOkreslaniaPredkosci();
		
		
		if( (DlugoscN < 40) && (Math.abs(DlugoscNE - DlugoscNW)<10) || (DlugoscN < 10))
		{
			KierunekPostaci-=8;
			if( KierunekPostaci <0) KierunekPostaci+=16;
		}
		else
		if( (DlugoscNE - DlugoscNW)> 10)
		{ 
			KierunekPostaci++;
			if( KierunekPostaci >15) KierunekPostaci-=16;
		}
		else
		if( (DlugoscNW - DlugoscNE)> 10)
		{
			KierunekPostaci--;
			if( KierunekPostaci <0) KierunekPostaci+=16;
		}
		for (Rectangle BlokSciany : Sciany )
		{
			if(BlokSciany.intersects(Postac))
			{
				UsunScianyB.setEnabled(true);
				DodajGraczaB.setEnabled(true);
				UsunGraczaB.setEnabled(false);
				KierunekPostaci = 17;
				xPostaci = -24.0;
				yPostaci = -24.0;
			}
		}
		
		oknogry.repaint();
		
	}
	/**
	 * Metoda zajmujaca sie rozmywaniem zbioru wejsciowego jakim jest zmierzona odleglosc przez sensory oraz dostosujaca predkosc do
	 * uzyskanych termow
	 */
	public void MetodaKlasyfikacjiIOkreslaniaPredkosci()
	{
		if (DlugoscNE<=45 && DlugoscNW<=45)
		{
			krotkiD = 1.0; sredniD = 0.0; dalekiD = 0.0;
			KlasyfikacjaOdleglosci.setText("Odleglosc : Blisko");
		}
		else
		if ((45 < DlugoscNE && DlugoscNE < 55) && (45 < DlugoscNW && DlugoscNW < 55))
			{
				if (DlugoscNE>DlugoscNW)
				{
					sredniD =((double)DlugoscNE - 45.0)/10;
					krotkiD = 1 - sredniD;
				}
				else
				{
					sredniD =((double)DlugoscNW - 45.0)/10;
					krotkiD = 1 - sredniD;
				}
				dalekiD = 0.0;
				KlasyfikacjaOdleglosci.setText("Odleglosc : Blisko ("+krotkiD+")");
				
			}
		else
		if ((55<=DlugoscNE && DlugoscNE<=95) && (55<=DlugoscNW && DlugoscNW<=95))
		{
			krotkiD = 0; sredniD = 1; dalekiD = 0;
			KlasyfikacjaOdleglosci.setText("Odleglosc : Srednio");
		}
		else
			if ((95 < DlugoscNE && DlugoscNE < 105) && (95 < DlugoscNW && DlugoscNW < 105))
			{
				if (DlugoscNE>DlugoscNW)
				{
					dalekiD =((double)DlugoscNE - 95.0)/10;
					sredniD = 1 - sredniD;
				}
				else
				{
					dalekiD =((double)DlugoscNW - 95.0)/10;
					sredniD = 1 - dalekiD;
				}
				krotkiD = 0.0;
				KlasyfikacjaOdleglosci.setText("Odleglosc : Srednio ("+sredniD+")");
			}
		else
		{
			krotkiD = 0; sredniD = 0; dalekiD = 1;
			KlasyfikacjaOdleglosci.setText("Odleglosc : Daleko");
		}
		
		Predkosc = (krotkiD + sredniD*2 + dalekiD*3)/(krotkiD + sredniD + dalekiD);
	}
	
	@Override
	/**
	 * Metoda MouseMotionListentera zwraca pozycje myszy
	 */
	public void mouseDragged(MouseEvent e) {
		PozycjaX=e.getX();
		PozycjaY=e.getY();
		PozycjaMyszy.setText("Pozycja Myszy = "+e.getX()+","+e.getY());
	}

	@Override
	/**
	 * Metoda MouseMotionListentera zwraca pozycje myszy
	 */
	public void mouseMoved(MouseEvent e) {
		PozycjaX=e.getX();
		PozycjaY=e.getY();
		PozycjaMyszy.setText("Pozycja Myszy = "+e.getX()+","+e.getY());
	}

	
		
		@Override
		/**
		 * Metoda MouseListenera - po puszczeniu przycisku myszy wywolywana jest metoda Dodaj Sciane ktora rysuje sciane od pozycji z ktorej zaczeto ciagnac myszke
		 */
		public void mouseReleased(MouseEvent e) {
			if (CzyRysujeSciany){
			System.out.println("ck   " +PozycjaX+"  "+ PozycjaY);
			DodajSciane(PoczatekScianyX,PoczatekScianyY,PozycjaX,PozycjaY);	
			
			}
		}
		
		@Override
		/**
		 * Metoda MouseListenera - w jednym przypadku  (czyRysujeSciany = true)  zbiera informacje o wspolzednych poczatku sciany
		 * 						   w drugim przypadku  (czyRysujePostac = true)  stawia postac w miejscu klikniecia
		 */
		public void mousePressed(MouseEvent e) {
			if (CzyRysujeSciany)
			{
				PoczatekScianyX = PozycjaX;
				PoczatekScianyY = PozycjaY;	
				System.out.println("cli-     "+PoczatekScianyX+"   "+ PoczatekScianyY);		
			}
			if (CzyRysujePostac)
			{
				xPostaci = PozycjaX-6;
				yPostaci = PozycjaY-6;
				KierunekPostaci = 0;
				CzyRysujePostac = false;
			}
		}
		
		@Override
		public void mouseExited(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseClicked(MouseEvent e) {}
}
