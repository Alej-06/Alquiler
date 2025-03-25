package tema8Alquiler;

import java.awt.EventQueue;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Tema8Alquiler {

	private JFrame frmAlquiler;
	private JTable tableUsuario;
	private JTable tableBicis;
	private JTextField textFieldUsuario;
	private JTextField textFieldBici;
	private JTextField textFieldNom;
	private JTextField textFieldEdad;
	private JTextField textFieldCuentaBanco;

	boolean comprobarExpReg(String cadena, String patron) {
		Pattern pat=Pattern.compile(patron);
		Matcher mat=pat.matcher(cadena);
		
		if (mat.matches()) {
			return true;
		}else {
			return false;
		}
	}
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Tema8Alquiler window = new Tema8Alquiler();
					window.frmAlquiler.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Tema8Alquiler() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAlquiler = new JFrame();
		frmAlquiler.setTitle("Alquiler");
		frmAlquiler.setBounds(100, 100, 937, 513);
		frmAlquiler.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAlquiler.getContentPane().setLayout(null);
		
		DefaultTableModel usuarios = new DefaultTableModel();
		usuarios.addColumn("Código");
		usuarios.addColumn("Nombre");
		usuarios.addColumn("Edad");
		usuarios.addColumn("Cuenta Bancaria");
		
		DefaultTableModel bicis = new DefaultTableModel();
		bicis.addColumn("Código");
		bicis.addColumn("Alquilada por");
		
		tableUsuario = new JTable(usuarios);
		tableUsuario.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int indice =tableUsuario.getSelectedRow();
				TableModel modelo = tableUsuario.getModel();
				
				textFieldUsuario.setText(String.valueOf(modelo.getValueAt(indice, 0)));
				textFieldNom.setText(String.valueOf( modelo.getValueAt(indice, 1)));
				textFieldEdad.setText(String.valueOf( modelo.getValueAt(indice, 2)));
				textFieldCuentaBanco.setText(String.valueOf(modelo.getValueAt(indice, 3)));

			}
		});
		tableUsuario.setBounds(72, 44, 289, 109);
		frmAlquiler.getContentPane().add(tableUsuario);
		
		tableBicis = new JTable(bicis);
		tableBicis.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int indice =tableBicis.getSelectedRow();
				TableModel modelo = tableBicis.getModel();
				
				textFieldBici.setText(String.valueOf(modelo.getValueAt(indice, 0)));
			}
		});
		tableBicis.setBounds(525, 54, 289, 96);
		frmAlquiler.getContentPane().add(tableBicis);
		
		JScrollPane scrollPaneUsuario = new JScrollPane(tableUsuario);
		scrollPaneUsuario.setBounds(29, 44, 346, 109);
		frmAlquiler.getContentPane().add(scrollPaneUsuario);
		
		JScrollPane scrollPaneBicis = new JScrollPane(tableBicis);
		scrollPaneBicis.setBounds(525, 44, 360, 109);
		frmAlquiler.getContentPane().add(scrollPaneBicis);
		
		textFieldUsuario = new JTextField();
		textFieldUsuario.setBounds(247, 212, 114, 19);
		frmAlquiler.getContentPane().add(textFieldUsuario);
		textFieldUsuario.setColumns(10);
		
		textFieldBici = new JTextField();
		textFieldBici.setBounds(525, 212, 114, 19);
		frmAlquiler.getContentPane().add(textFieldBici);
		textFieldBici.setColumns(10);
		
		textFieldNom = new JTextField();
		textFieldNom.setBounds(198, 293, 177, 19);
		frmAlquiler.getContentPane().add(textFieldNom);
		textFieldNom.setColumns(10);
		
		textFieldEdad = new JTextField();
		textFieldEdad.setBounds(198, 338, 177, 19);
		frmAlquiler.getContentPane().add(textFieldEdad);
		textFieldEdad.setColumns(10);
		
		textFieldCuentaBanco = new JTextField();
		textFieldCuentaBanco.setBounds(198, 380, 177, 19);
		frmAlquiler.getContentPane().add(textFieldCuentaBanco);
		textFieldCuentaBanco.setColumns(10);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(49, 295, 70, 15);
		frmAlquiler.getContentPane().add(lblNombre);
		
		JLabel lblEdad = new JLabel("Edad");
		lblEdad.setBounds(49, 340, 70, 15);
		frmAlquiler.getContentPane().add(lblEdad);
		
		JLabel lblCuentaBancaria = new JLabel("Cuenta Bancaria");
		lblCuentaBancaria.setBounds(49, 382, 131, 15);
		frmAlquiler.getContentPane().add(lblCuentaBancaria);
		
		try {
		
			Connection con = ConnectionSingleton.getConnection();
			Statement muestra = con.createStatement();
			ResultSet visualizar=muestra.executeQuery("SELECT * FROM usuarios WHERE id !=0");
			usuarios.setRowCount(0);
			while(visualizar.next()) {
				Object[] row = new Object [4];
				row[0]= visualizar.getInt("id");
				row[1] = visualizar.getString("nombre");
				row[2] = visualizar.getInt("edad");
				row[3] = visualizar.getString("cuenta_banco");
				usuarios.addRow(row);
			}
			
			Statement muestrabici = con.createStatement();
			ResultSet visualizarbici=muestrabici.executeQuery("SELECT * FROM bicis ORDER BY cod");
			bicis.setRowCount(0);
			while(visualizarbici.next()) {
				Object[] row = new Object [2];
				row[0] = visualizarbici.getInt("cod");
				row[1] = visualizarbici.getInt("id_usuario");
				bicis.addRow(row);
			}
			
		JButton btnAlquilar = new JButton("ALQUILAR");
		btnAlquilar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int codp=tableBicis.getSelectedRow();
				TableModel model= tableBicis.getModel();
				
				if ( (int) model.getValueAt(codp, 1) != 0) {
					JOptionPane.showMessageDialog(frmAlquiler, "No puedes alquilar una bici que ya está alquilada","Advertencia",JOptionPane.ERROR_MESSAGE);
				}else {
				
				String usuario=textFieldUsuario.getText();
				String bici=textFieldBici.getText();
				
				try {
					Connection con =ConnectionSingleton.getConnection();
					PreparedStatement comprobante = con.prepareStatement("SELECT COUNT(id_usuario) AS cuenta FROM bicis WHERE id_usuario=?");	//Nota mental, asegurate de que el código es el del USUARIO
					comprobante.setInt(1, Integer.parseInt(usuario));
					ResultSet comprobar = comprobante.executeQuery();
			
					comprobar.next();

					if (comprobar.getInt("cuenta")!=0) {	//Tienes que asegurarte de hacer el next de antemano. Y los alias tienen que hacerse bien
						JOptionPane.showMessageDialog(frmAlquiler, "No puedes alquilar más de una bicicleta","Advertencia",JOptionPane.ERROR_MESSAGE);
					}else {
					
					PreparedStatement alquiler = con.prepareStatement("UPDATE bicis SET id_usuario =? WHERE cod=?");
					alquiler.setString(1, usuario);
					alquiler.setString(2, bici);
					alquiler.executeUpdate();
					alquiler.close();
					JOptionPane.showMessageDialog(frmAlquiler, "Se ha alquilado la bicicleta");
					Statement muestrabici = con.createStatement();
					ResultSet visualizarbici=muestrabici.executeQuery("SELECT * FROM bicis ORDER BY cod");
					bicis.setRowCount(0);
					while(visualizarbici.next()) {
						Object[] row = new Object [2];
						row[0] = visualizarbici.getInt("cod");
						row[1] = visualizarbici.getInt("id_usuario");
						bicis.addRow(row);	
					}
					textFieldUsuario.setText("");
					textFieldBici.setText("");
					con.close();
					}
					
					comprobante.close();
					comprobar.close();
				}catch (SQLException ex) {
					JOptionPane.showMessageDialog(frmAlquiler, ex.getMessage(),"Advertencia",JOptionPane.ERROR_MESSAGE);
				}
				
			
				}
			}
		});
		btnAlquilar.setBounds(387, 183, 117, 25);
		frmAlquiler.getContentPane().add(btnAlquilar);
		
		JButton btnDevolver = new JButton("DEVOLVER");
		btnDevolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int codp=tableBicis.getSelectedRow();
				TableModel model= tableBicis.getModel();
				
				if ( (int) model.getValueAt(codp, 1) == 0) {
					JOptionPane.showMessageDialog(frmAlquiler, "No puedes devolver una bici que no está alquilada","Advertencia",JOptionPane.ERROR_MESSAGE);
				}else {
					String bici=textFieldBici.getText();
					
					try {
						Connection con =ConnectionSingleton.getConnection();
						PreparedStatement devolver = con.prepareStatement("UPDATE bicis  SET id_usuario=0 WHERE cod=?");
						devolver.setString(1, bici);
						devolver.executeUpdate();
						devolver.close();
						JOptionPane.showMessageDialog(frmAlquiler, "Se ha devuelto la bicicleta");
						Statement muestrabici = con.createStatement();
						ResultSet visualizarbici=muestrabici.executeQuery("SELECT * FROM bicis ORDER BY cod");
						bicis.setRowCount(0);
						while(visualizarbici.next()) {
							Object[] row = new Object [2];
							row[0] = visualizarbici.getInt("cod");
							row[1] = visualizarbici.getInt("id_usuario");
							bicis.addRow(row);	
						}
						textFieldBici.setText("");
						con.close();
					}catch (SQLException ex) {
						JOptionPane.showMessageDialog(frmAlquiler, ex.getMessage(),"Advertencia",JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnDevolver.setBounds(387, 226, 117, 25);
		frmAlquiler.getContentPane().add(btnDevolver);
		
		JButton btnInsertar = new JButton("AÑADIR");
		btnInsertar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textFieldNom.getText().length()==0) {
					JOptionPane.showMessageDialog(frmAlquiler, "El nombre está vacío","Advertencia",JOptionPane.ERROR_MESSAGE);
				}else if (textFieldEdad.getText().length()==0) {
					JOptionPane.showMessageDialog(frmAlquiler, "La edad está vacía","Advertencia",JOptionPane.ERROR_MESSAGE);
				}else if (textFieldCuentaBanco.getText().length()==0) {
					JOptionPane.showMessageDialog(frmAlquiler, "La cuenta bancaria está vacía","Advertencia",JOptionPane.ERROR_MESSAGE);
				}else if (!comprobarExpReg(textFieldNom.getText(), "^\\D+$")){
					JOptionPane.showMessageDialog(frmAlquiler, "El nombre debe ser una cadena de caracteres alfabéticos","Advertencia",JOptionPane.ERROR_MESSAGE);
				}else if (!comprobarExpReg(textFieldEdad.getText(), "^[0-9]+$")){
					JOptionPane.showMessageDialog(frmAlquiler, "La edad debe ser un número entero","Advertencia",JOptionPane.ERROR_MESSAGE);
				}else if (!comprobarExpReg(textFieldCuentaBanco.getText(), "^[0-9]{20}$")){
					JOptionPane.showMessageDialog(frmAlquiler, "La cuenta bancaria debe ser un número entero de 20 caracteres","Advertencia",JOptionPane.ERROR_MESSAGE);
				}else {
				try {
					Connection con =ConnectionSingleton.getConnection();
					PreparedStatement insUsu = con.prepareStatement("INSERT INTO usuarios (nombre,edad,cuenta_banco) VALUES (?,?,?)");
					String nom = textFieldNom.getText();
					String edad = textFieldEdad.getText();
					String cuenta =textFieldCuentaBanco.getText();
					insUsu.setString(1, nom);
					insUsu.setString(2, edad);
					insUsu.setString(3, cuenta);
					insUsu.executeUpdate();
					JOptionPane.showMessageDialog(frmAlquiler, "Se ha añadido el usuario");
					Statement muestra = con.createStatement();
					ResultSet visualizar=muestra.executeQuery("SELECT * FROM usuarios WHERE id !=0");
					usuarios.setRowCount(0);
					while(visualizar.next()) {
						Object[] row = new Object [4];
						row[0]= visualizar.getInt("id");
						row[1] = visualizar.getString("nombre");
						row[2] = visualizar.getInt("edad");
						row[3] = visualizar.getString("cuenta_banco");
						usuarios.addRow(row);
					}
				}catch (SQLException ex) {
					JOptionPane.showMessageDialog(frmAlquiler, ex.getMessage(),"Advertencia",JOptionPane.ERROR_MESSAGE);
				}
			}
			}
		});
		btnInsertar.setBounds(49, 418, 117, 25);
		frmAlquiler.getContentPane().add(btnInsertar);
		
		JButton btnCrearBici = new JButton("CREAR BICI");
		btnCrearBici.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Connection con =ConnectionSingleton.getConnection();
					PreparedStatement insBici = con.prepareStatement("INSERT INTO bicis (id_usuario) VALUES (0)");
					insBici.executeUpdate();
					insBici.close();
					JOptionPane.showMessageDialog(frmAlquiler, "Se ha añadido una bicicleta");
					Statement muestrabici = con.createStatement();
					ResultSet visualizarbici=muestrabici.executeQuery("SELECT * FROM bicis ORDER BY cod");
					bicis.setRowCount(0);
					while(visualizarbici.next()) {
						Object[] row = new Object [2];
						row[0] = visualizarbici.getInt("cod");
						row[1] = visualizarbici.getInt("id_usuario");
						bicis.addRow(row);	
					}
					con.close();
				}catch (SQLException ex) {
					JOptionPane.showMessageDialog(frmAlquiler, ex.getMessage(),"Advertencia",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnCrearBici.setBounds(641, 330, 117, 25);
		frmAlquiler.getContentPane().add(btnCrearBici);
		}catch (SQLException e) {
			JOptionPane.showMessageDialog(frmAlquiler, e.getMessage(),"Advertencia",JOptionPane.ERROR_MESSAGE);
		}
	}
}
