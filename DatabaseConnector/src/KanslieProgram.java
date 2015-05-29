import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;


public class KanslieProgram {

	private String userName = "ae8081", password = "rotmos";
	
	public static void main(String [] args){
		KanslieProgram kp = new KanslieProgram();
		kp.action();
	}

	public void addBand(String land, String info, String genre, String bandnamn, String kontaktperson) {
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://195.178.235.60:3306/ae8081",
												userName, password);
		
			String query = "insert into Band(Land, Info, Genre, Bandnamn, Kontaktperson) values (?, ?, ?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, land);
			stmt.setString(2, info);
			stmt.setString(3, genre);
			stmt.setString(4, bandnamn);
			stmt.setString(5, kontaktperson);
			
			stmt.execute();
			JOptionPane.showMessageDialog(null, "Sparat i databasen");
			stmt.close();
			
		} catch (Exception e) {
			System.err.println(e);
		  }
	}
	public void addScen(String namn, String plats, String kapacitet, String scenansvarig) {
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://195.178.235.60:3306/ae8081",
												userName, password);
		
			String query = "insert into Scen(Namn, Plats, Kapacitet, Scenansvarig) values ( ?, ?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, namn);
			stmt.setString(2, plats);
			stmt.setString(3, kapacitet);
			stmt.setString(4, scenansvarig);
			
			
			stmt.execute();
			JOptionPane.showMessageDialog(null, "Sparat i databasen");
			stmt.close();
			
		} catch (Exception e) {
			System.err.println(e);
		  }
	}
	public void addGig(String bandnamn, String scen, String tid) {
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://195.178.235.60:3306/ae8081",
												userName, password);
		
			String query = "insert into Spelschema(Bandnamn, Scennamn, Tid) values ( ?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, bandnamn);
			stmt.setString(2, scen);
			stmt.setString(3, tid);

			stmt.execute();
			JOptionPane.showMessageDialog(null, "Sparat i databasen");
			stmt.close();
			
		} catch (Exception e) {
			System.err.println(e);
		  }
	}
	
	public void addBandmedlem(String bandnamn, String bandmedlem){
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://195.178.235.60:3306/ae8081",
												userName, password);
		
			String query = "insert into Medlemmar(Bandnamn, Namn) values ( ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, bandnamn);
			stmt.setString(2, bandmedlem);

			stmt.execute();
			JOptionPane.showMessageDialog(null, "Sparat i databasen");
			stmt.close();
			
		} catch (Exception e) {
			System.err.println(e);
		}
		  
	}
	public String menyLista(String query){
		String list = "";
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://195.178.235.60:3306/ae8081",
												userName, password);
			Statement stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery(query);
			if(query.contains("Kontaktperson")){
				while(rs.next()){
					list+="Bandnamn: " + rs.getString(1) + "\n" + "Kontaktperson: " + rs.getString(2) + "\n" + "Kontaktpersonens personnummer:" +  rs.getString(3) + "\n\n";
				}
			}
			else if(query.contains("Scenansvarig")){
				while(rs.next()){
					list+="Scennamn: " + rs.getString(1) + "\n" + "Scenansvarig: " + rs.getString(2) + "\n" + "Scenansvarigs personnummer:" +  rs.getString(3) + "\n\n";
				}
			}
			else if(query.contains("Spelschema")){
				list = "SpelSchema:\n\nTid:                                               " + "Scen:                                        " + "Bandnamn:" + "\n"; 
				while(rs.next()){
					list+= rs.getString(1) + "                       " + rs.getString(2) + "                       " + rs.getString(3) + "\n"; 
				}
			}
			else if(query.contains("Band")){
				while(rs.next()){
					list+=rs.getString(4) + "\n";
				}
			}
			else if(query.contains("Arbetare")){		
			while (rs.next()){
				list += rs.getString(1) + "\t"  + rs.getString(2) + "\n";
				}
			}
			else if(query.contains("Scen")){
				while(rs.next()){
					list+=rs.getString(1) + "\n";
				}
			}
		
			stmt.close();
			} catch (Exception e) {
			System.err.println(e);
		  }
		return list;
	}
	public int menu() {
        int choice;
        String menu = "\nMENY\n" +
                "1. Lägg till ett band\n" +
                "2. Lägg till en scen\n" +
                "3. Lägg till bandmedlem åt band\n" +
                "4. Lägg till spelning\n" +
                "5. Se kontaktpersoner\n" +
                "6. Se scenansvariga\n" +
                "7. Lägg till arbetare\n" +
                "8. Se spelschema\n" +
                "9. Stäng av programmet\n\n" +
                "Ange val";
        
        do {
            choice = Integer.parseInt(JOptionPane.showInputDialog(menu));
        }while(choice<1 || choice>9);
        return choice;
    }
    
    public void action() {
        int choice = menu();
        while(choice!=9) {
            switch (choice) {
			    case 1:
			    	String bandnamn = JOptionPane.showInputDialog("Ange bandets namn:");
			        String land = JOptionPane.showInputDialog("Ange bandets hemland:");
			        String info = JOptionPane.showInputDialog("Skriv lite intressant info om bandet:");
			        String genre = JOptionPane.showInputDialog("Ange bandets genre:");
			        String kontaktperson = JOptionPane.showInputDialog("Ange bandets kontaktpersons personnummer:(Personnummer skrivs ÅÅÅÅMMDD-XXXX)\n" + menyLista("select * from Arbetare"));
			        addBand(land, info, genre, bandnamn, kontaktperson);
			        break;
			    case 2:
			    	String namn = JOptionPane.showInputDialog("Ange scenens namn:");
			        String plats = JOptionPane.showInputDialog("Ange scenens adress:");
			        String kapacitet = JOptionPane.showInputDialog("Kapacitet:");
			        String scenansvarig = JOptionPane.showInputDialog("Ange scenansvarigs personnummer(Personnummer skrivs ÅÅÅÅMMDD-XXXX):\n" + menyLista("select * from Arbetare"));
			        addScen(namn, plats, kapacitet, scenansvarig);
			        break;
			    case 3:
			    	namn = JOptionPane.showInputDialog("Ange bandet:\n" + menyLista("select * from Band"));
			    	String bandmedlem = JOptionPane.showInputDialog("Ange namnet på bandmedlemmen:");
			    	addBandmedlem(namn, bandmedlem);
			    	break;
			    case 4:
			       bandnamn = JOptionPane.showInputDialog("Ange bandets namn\n" + menyLista("select * from Band"));
			       String scen = JOptionPane.showInputDialog("Ange bandets namn\n" + menyLista("select * from Scen"));
			       String tid = JOptionPane.showInputDialog("Ange tid:(ÅR-MÅNAD-DATUM HH:MM:SS ");
			       addGig(bandnamn, scen, tid);
			       break;
			    case 5:
			       JOptionPane.showMessageDialog(null, menyLista("select Band.Bandnamn, Arbetare.Namn, Band.Kontaktperson from Band inner join Arbetare on Band.Kontaktperson=Arbetare.Personnummer"));
			        break;
			    case 6:
			        JOptionPane.showMessageDialog(null, menyLista("select Scen.Namn, Arbetare.Namn, Scen.Scenansvarig from Scen inner join Arbetare on Scen.Scenansvarig=Arbetare.Personnummer"));
			        break;
			    case 7:
			    	String personnummer = JOptionPane.showInputDialog("Ange personummer:\n");
			    	namn = JOptionPane.showInputDialog("Ange personens namn:\n");
			    	addArbetare(personnummer, namn);
			    	break;
			    case 8:
			    	JOptionPane.showMessageDialog(null, menyLista("select * from Spelschema"));
			    	break;
			    case 9:
			    	System.exit(0);
			}
            choice = menu();
        }
    }

	private void addArbetare(String personnummer, String namn) {
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://195.178.235.60:3306/ae8081",
												userName, password);
		
			String query = "insert into Arbetare(Namn, Personnummer) values ( ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, namn);
			stmt.setString(2, personnummer);

			stmt.execute();
			JOptionPane.showMessageDialog(null, "Sparat i databasen");
			stmt.close();
			
		} catch (Exception e) {
			System.err.println(e);
		}
		  
		
	}

}