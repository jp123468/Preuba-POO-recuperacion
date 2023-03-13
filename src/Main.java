import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Main {


    private JTextField textFieldNOMBRE;
    private JTextField textFieldAPELLIDO;
    private JTextField textFieldNUM_TELF;
    private JTextField textFieldDIRECCION;

    private JButton BUSCARButton;
    private JButton ACTUALIZARButton;
    private JButton ELIMINARButton;
    private JButton CREARButton;
    private JTextField textFieldCIUDAD;
    private JPanel panel1;
    private JButton MOSTRARButton;
    private JTextField textFieldPAIS;

    public Main (){


        CREARButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Connection conn;
                    try{
                        conn = getConnection();
                        PreparedStatement ps = conn.prepareStatement("INSERT INTO agenda1( nombre, apellido, numero_tlf, pais, ciudad,direccion) VALUES(?,?,?,?,?,?)");
                        try{

                                ps.setString(1, textFieldNOMBRE.getText());
                                ps.setString(2, textFieldAPELLIDO.getText());
                                ps.setInt(3, Integer.parseInt(String.valueOf(textFieldNUM_TELF.getX())));
                                ps.setString(4, textFieldPAIS.getText());
                                ps.setString(5, textFieldCIUDAD.getText());
                                ps.setString(6, textFieldDIRECCION.getText());

                                //Integer.parseInt(ComboboxAnio.getSelectedItem().toString()
                                //String ar =comboBoxPAIS.getSelectedItem().toString();

                        }catch (SQLException es){
                            System.out.println("Error: " + es + "||||");
                            JOptionPane.showMessageDialog(null,"Ingrese bien los datos de la persona");
                        }


                        System.out.println(ps);
                        int res = ps.executeUpdate();

                        if(res > 0){
                            JOptionPane.showMessageDialog(null, "Contacto ingresado exitosamente en la agenda");
                        }else{
                            JOptionPane.showMessageDialog(null, "El Contacto No pudo ser Ingresada en el sistema");
                        }
                        conn.close();
                    }catch (HeadlessException | SQLException f){
                        System.out.println(f);
                    }

                }
            });



        ACTUALIZARButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Connection conn;

                    try{
                        conn = getConnection();
                        PreparedStatement ps = conn.prepareStatement("UPDATE AGENDA1 SET  nombre = ?, apellido = ?, numerotlf = ?, pais = ?, ciudad=? , direccion=? WHERE nombre ="+textFieldNOMBRE.getText() );
                        try{
                                ps.setString(1, textFieldNOMBRE.getText());
                                ps.setString(2, textFieldAPELLIDO.getText());
                                ps.setInt(3, Integer.parseInt(String.valueOf(textFieldNUM_TELF.getX())));
                                ps.setString(4, textFieldPAIS.getText());
                                ps.setString(5, textFieldCIUDAD.toString());
                                ps.setString(6, textFieldDIRECCION.toString());

                        }catch (SQLException es){
                            System.out.println("Error: " + es + "||||");
                            JOptionPane.showMessageDialog(null,"Ingrese bien los datos del COntacto");
                        }


                        System.out.println(ps);
                        int res = ps.executeUpdate();

                        if(res > 0){
                            JOptionPane.showMessageDialog(null, "Contacto modificado correctamente");
                        }else{
                            JOptionPane.showMessageDialog(null, "El Contacto no se pudo modificar");
                        }
                        conn.close();
                    }catch (HeadlessException | SQLException f){
                        System.out.println(f);
                    }
                }
            });
            //ELIMINAR
           ELIMINARButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Connection conn;
                    try {
                        conn = getConnection();
                        PreparedStatement ps = conn.prepareStatement("DELETE FROM agenda1 WHERE nombre = ?");
                        ps.setString(1, textFieldNOMBRE.getText());
                        int rowsAffected = ps.executeUpdate(); // Cambiar a executeUpdate()
                        conn.close();

                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(null, "Contacto eliminado correctamente");
                        } else {
                            JOptionPane.showMessageDialog(null, "No se eliminó ningún Contacto");
                        }
                    } catch (SQLException ex) {
                        System.out.println("Error: " + ex.getMessage());
                        JOptionPane.showMessageDialog(null, "Ingrese bien los datos del Contacto");
                    }
                }
            });
        BUSCARButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Connection conn;
                    ResultSet rs;
                    try{
                        conn = getConnection();


                        PreparedStatement ps = conn.prepareStatement("SELECT * FROM agenda1 WHERE nombre= ?" );
                        ps.setString(1, textFieldNOMBRE.getText());

                        rs = ps.executeQuery();

                        try{

                                if(rs.next()){
                                    textFieldNOMBRE.setText(rs.getString("NOMBRE"));
                                    textFieldAPELLIDO.setText(rs.getString("APELLIDO"));
                                    textFieldNUM_TELF.setText( Integer.toString(rs.getInt("NUMERO DE TELEFONO")));
                                    textFieldPAIS.setText(rs.getString("PAIS"));
                                    textFieldCIUDAD.setText(rs.getString("CIUDAD"));
                                    textFieldDIRECCION.setText(rs.getString("DIRECCION"));
                                    }
                                else{
                                    System.out.println("Error no funciona");
                                    JOptionPane.showMessageDialog(null,"Ingrese bien los datos del Contacto");
                                }

                        }catch (SQLException es){
                            System.out.println("Error: " + es + "||||");
                            JOptionPane.showMessageDialog(null,"Ingrese bien los datos del Contacto");
                        }
                        conn.close();
                    }catch (HeadlessException | SQLException f){
                        System.out.println(f);
                    }
                }
            });
        }


        public static Connection getConnection(){
            Connection conn = null;
            String base = "agenda";
            String url = "jdbc:mysql://localhost:3306/" + base;
            String user = "root";
            String password = "POObjetos1.";

            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(url,user,password);
            }catch (ClassNotFoundException | SQLException ex){
                System.out.printf("Error: " + ex);
            }
            return conn;
        }


    public static void main(String[] args) {
            JFrame frame = new JFrame("AGENDA");
            frame.setContentPane(new Main().panel1);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        }

        private void createUIComponents() {
            // TODO: place custom component creation code here
        }
}
