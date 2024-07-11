package gemiSavasi;

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Oyun extends JFrame implements ActionListener
{	
	Gemi[] gemiler;
	ArrayList<Integer> gemiKonumlari;
	int[] gemiSagliklari;
	JPanel[] paneller;
	JPanel[] altPaneller;
	JButton baslaButonu;
	JButton[] konumlar;	
	JLabel[] basliklar;
	JLabel duzenlenenKapakResmi;
	int sira = 0;
	static int batanGemiSayisi = 0;
	boolean oyunBitti;
	
	public Oyun()
	{
		super("GEMİ SAVAŞI");
		int en = 770, boy = 770;
		this.setSize(en, boy);
		this.setLayout(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		
		paneller = new JPanel[2];
		for(int i=0 ; i<paneller.length ; i++) {
			paneller[i] = new JPanel();
			paneller[i].setSize(en, boy);
			if(i==0) {
				paneller[i].setBackground(Color.BLACK);
				paneller[i].setLayout(new FlowLayout());
				paneller[i].add(Box.createRigidArea(new Dimension(0, 100)));
				
				baslaButonu = new JButton("Başla");		
				baslaButonu.setPreferredSize(new Dimension(400, 100));
				baslaButonu.setFont(new Font("Verdana",Font.BOLD ,40));
				baslaButonu.setBackground(Color.BLUE);
				baslaButonu.setForeground(Color.WHITE);
				baslaButonu.setBorder(BorderFactory.createLineBorder(Color.BLACK, 7));		
				baslaButonu.addActionListener(this);								
				
				ImageIcon kapakResmi = new ImageIcon("GemiResimleri/7.jpg");				
				Image duzenlenecekResim = kapakResmi.getImage(); 
				Image duzenlenenResim = duzenlenecekResim.getScaledInstance(600, 350, Image.SCALE_SMOOTH);				
				duzenlenenKapakResmi = new JLabel(new ImageIcon(duzenlenenResim));
			}else {
				paneller[i].setLayout(null);
			}						
		}		
				
		altPaneller = new JPanel[2];
		for(int i=0 ; i<altPaneller.length ; i++) {
			altPaneller[i] = new JPanel();
			altPaneller[i].setBounds(0, i*60, en-13, (i==0)? 60 : 675);
			altPaneller[i].setBackground(Color.BLACK);			
			altPaneller[i].setLayout((i==0)? new FlowLayout() : new GridLayout(10,9));
			paneller[1].add(altPaneller[i]);
		}
		
		this.add(paneller[0]);
		
		gemiler = new Gemi[6];
		gemiSagliklari = new int[gemiler.length];
		for(int i=1 ; i<=gemiler.length ; i++) {
			ImageIcon gemiResmi = new ImageIcon("GemiResimleri/"+i+".png");
			Image duzenlenecekResim = gemiResmi.getImage(); 
			Image duzenlenenResim = duzenlenecekResim.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			gemiSagliklari[i-1] = 100;  // Gemi sağlıkları buradan değiştirilebilir.
			gemiler[i-1] = new Gemi(new ImageIcon(duzenlenenResim), gemiSagliklari[i-1]);
		}	
		
		basliklar = new JLabel[2];
		for(int i=0 ; i<basliklar.length ; i++) {
			basliklar[i] = (i==0)? new JLabel("GEMİ SAVAŞINA HOŞGELDİNİZ") : new JLabel("SIRA 1. GEMİDE ATEŞ ETMEK İSTEDİĞİNİZ GEMİYİ SEÇİNİZ");
			setLabel(basliklar[i], Color.LIGHT_GRAY, (i==0)? 35 : 20);
			if(i==1)
				altPaneller[0].add(basliklar[i]);
		}
		
		paneller[0].add(basliklar[0]);
		paneller[0].add(baslaButonu);
		paneller[0].add(duzenlenenKapakResmi);

		gemiKonumlari = new ArrayList<>();
		konumlar = new JButton[90];
		for(int i=0 ; i<konumlar.length ; i++) {
			if((i-2)%36==0 || (i-6)%36==0) {
				konumlar[i] = new JButton(gemiler[sira].getResim());
				konumlar[i].setText("<html><br>Can:<br>" + gemiler[sira].getCan() + "</html>");
				konumlar[i].setForeground(Color.RED);
				gemiKonumlari.add(i);
				sira++;				
			}
			else
				konumlar[i] = new JButton();
			konumlar[i].setBackground(Color.BLUE);
			konumlar[i].setBorderPainted(false);
			konumlar[i].addActionListener(this);
			altPaneller[1].add(konumlar[i]);			
		}
		sira = 0;		
		
		this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	    if(oyunBitti) {
	    	
	    }else {
	    	JButton basilanButon = (JButton) e.getSource();

		    if (basilanButon == baslaButonu) {
		        paneller[0].setVisible(false);
		        this.add(paneller[1]);
		        konumlar[gemiKonumlari.get(0)].setBackground(Color.GREEN);
		    } else {
		        for (int i = 0; i < konumlar.length; i++) {
		            if (basilanButon == konumlar[i]) {
		                if (basilanButon.getIcon() != null && i != gemiKonumlari.get(sira)) {
		                    if (basilanButon.getBackground().equals(Color.ORANGE)) {
		                        JOptionPane.showMessageDialog(null, "Ateş etmek istediğiniz gemi çoktan batmış !");
		                    } else {
		                        int mevcutGemi = -1;
		                        for (int j = 0; j < gemiKonumlari.size(); j++) {
		                            if (gemiKonumlari.get(j) == i) {
		                                mevcutGemi = j;
		                                break;
		                            }
		                        }
		                        
		                        System.out.println("Evet");
		                        konumlar[gemiKonumlari.get(sira)].setBackground(Color.BLUE);
		                        gemiSagliklari[mevcutGemi] -= 20;
		                        gemiler[mevcutGemi].setCan(gemiSagliklari[mevcutGemi]);
		                        konumlar[i].setText("<html><br>Can:<br>" + gemiler[mevcutGemi].getCan() + "</html>");
		                        if (gemiSagliklari[mevcutGemi] <= 0) { 
		                            konumlar[i].setBackground(Color.ORANGE);
		                            batanGemiSayisi++;
		                        }

		                        // Sıradaki gemiyi seçme mantığı
		                        do {
		                            sira++;
		                            if (sira == gemiler.length) {
		                                sira = 0;
		                            }
		                        } while (konumlar[gemiKonumlari.get(sira)].getBackground().equals(Color.ORANGE));

		                        // Seçilen geminin rengini yeşil yapma
		                        konumlar[gemiKonumlari.get(sira)].setBackground(Color.GREEN);
		                        
		                        if (gemiSagliklari[mevcutGemi] <= 0) { 
		                            if(batanGemiSayisi == 5) {
		                            	JOptionPane.showMessageDialog(null, "Oyun Bitti ! " + (sira+1) + ".gemi oyunu kazandı.");
		                            	oyunBitti = true;
		                            }else
		                            	JOptionPane.showMessageDialog(null, "" + (mevcutGemi+1) + ". gemiyi batırdınız. Sıra " + (sira+1) + ". gemide.");
		                        }else
		                        	JOptionPane.showMessageDialog(null, "" + (mevcutGemi+1) + ". gemiye ateş ettiniz. Sıra " + (sira+1) + ". gemide.");	                        
		                    }
		                } else {
		                	if(basilanButon.getBackground().equals(Color.BLUE))
		                		JOptionPane.showMessageDialog(null, "Suya değil gemilere ateş etmelisiniz !");
		                	else
		                		JOptionPane.showMessageDialog(null, "Kendi geminize değil diğer gemilere ateş etmelisiniz !");
		                }
		            }
		        }
		    }
	    }
	}
	
	public void setLabel(JLabel label, Color renk, int boyut)
	{
		label.setForeground(renk);
		label.setFont(new Font("Verdana",Font.BOLD ,boyut));
		label.setHorizontalAlignment(0);
	}

}
