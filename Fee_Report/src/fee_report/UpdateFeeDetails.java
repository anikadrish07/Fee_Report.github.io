/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package fee_report;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
//import java.util.DateFormat;
import javax.swing.JOptionPane;

/**
 *
 * @author ANIK ADRISH MAJHI
 */
public class UpdateFeeDetails extends javax.swing.JFrame {

    Connection con;
    /**
     * Creates new form Add_Fees
     */
    public UpdateFeeDetails() {
        initComponents();
        displayCash();
        StreamComboBox();
        int receiptNo=ReceiptNo();
        txt_RecieptNo.setText(Integer.toString(receiptNo));
        
        setRecords();
    }
    
    public void displayCash(){
        lbl_DDNo.setVisible(false);
        lbl_ChequeNo.setVisible(false);
        lbl_BankName.setVisible(false);
        txt_DDNo.setVisible(false);
        txt_ChequeNo.setVisible(false);
        txt_BankName.setVisible(false);
    }

    
    public  boolean validation(){
        
        if(DateChooser.getDate()==null){
            JOptionPane.showMessageDialog(this, "please enter date");
            return false;
        }
        
        if(combo_PaymentMode.getSelectedItem().toString().equalsIgnoreCase("Cheque")){
            if (txt_ChequeNo.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "please enter cheque no");
                return false;
            }
            if (txt_BankName.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "please enter bank name");
                return false;
            }
        }
        if(combo_PaymentMode.getSelectedItem().toString().equalsIgnoreCase("DD")){
            if (txt_DDNo.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "please enter DD no");
                return false;
            }
            if (txt_BankName.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "please enter bank name");
                return false;
            }
        }
        if(combo_PaymentMode.getSelectedItem().toString().equalsIgnoreCase("Card")){
            
            if (txt_BankName.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "please enter bank name");
                return false;
            }
        }
        if(txt_RecievedFrom.getText().equals("")){
            JOptionPane.showMessageDialog(this, "please enter username");
            return false;
        }
        if(txt_Amount.getText().equals("")){
            JOptionPane.showMessageDialog(this, "please enter amount");
            return false;
        }
        return true;
    }
    
    public void StreamComboBox(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/fees_management","root","");
           PreparedStatement st = con.prepareStatement("select StreamName from stream");
           ResultSet rs = st.executeQuery();
           while(rs.next()){
               combo_Stream.addItem(rs.getString("StreamName"));
           }
           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public int ReceiptNo(){
        int receiptNo = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/fees_management","root","");
           PreparedStatement st = con.prepareStatement("select max(reciept_no) from fees_details");
           ResultSet rs = st.executeQuery();
           if(rs.next()==true){
               receiptNo=rs.getInt(1);
           }
           
        } catch (Exception e) {
            e.printStackTrace();
        }
        return receiptNo+1;
    }
    
    public String updateData(){
        String status = "";
        
        int recieptNo = Integer.parseInt(txt_RecieptNo.getText());
        String studentName = txt_RecievedFrom.getText();
        String rollNo = txt_RollNo.getText();
        String paymentMode = combo_PaymentMode.getSelectedItem().toString();
        String chequeNo = txt_ChequeNo.getText();
        String bankName = txt_BankName.getText();
        String ddNo = txt_DDNo.getText();
        String streamName = txt_StreamName.getText();
        String gstin = txt_GSTINNo.getText();
        float totalAmount = Float.parseFloat(txt_Total.getText());
        SimpleDateFormat dateF = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateF.format(DateChooser.getDate());
        float initialAmonut = Float.parseFloat(txt_Amount.getText());
        float cgst = Float.parseFloat(txt_cgst.getText());
        float sgst = Float.parseFloat(txt_sgst.getText());
        String totalInWord = txt_TotalInWord.getText();
        String remark = txt_Remark.getText();
        int year1 = Integer.parseInt(txt_Year1.getText());
        int year2 = Integer.parseInt(txt_Year2.getText());
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/fees_management","root","");
           PreparedStatement st = con.prepareStatement("update fees_details set student_name=?,roll_no=?,payment_mode=?,"
                   + "cheque_no=?,bank_name=?,dd_no=?,course_name=?,gstin=?,total_amount=?,date=?,amount=?,cgst=?,sgst=?,total_in_words=?,"
                   + "remark=?,year1=?,year2=? where reciept_no=?");
           
           
           st.setString(1, studentName);
           st.setString(2, rollNo);
           st.setString(3, paymentMode);
           st.setString(4, chequeNo);
           st.setString(5, bankName);
           st.setString(6, ddNo);
           st.setString(7, streamName);
           st.setString(8, gstin);
           st.setFloat(9, totalAmount);
           st.setString(10, date);
           st.setFloat(11, initialAmonut);
           st.setFloat(12, cgst);
           st.setFloat(13, sgst);
           st.setString(14, totalInWord);
           st.setString(15, remark);
           st.setInt(16, year1);
           st.setInt(17, year2);
           st.setInt(18, recieptNo);
           
           int rowCount = st.executeUpdate();
           if(rowCount == 1){
               status = "success";
           }
           else{
               status = "failed";
           }
           
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }
    
    public void setRecords(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/fees_management","root","");
           PreparedStatement st = con.prepareStatement("select * from fees_details order by reciept_no desc limit 1");
           ResultSet rs = st.executeQuery();
           rs.next();
           txt_RecieptNo.setText(rs.getString("reciept_No"));
           combo_PaymentMode.setSelectedItem(rs.getString("payment_mode"));
           txt_ChequeNo.setText(rs.getString("cheque_no"));
           txt_DDNo.setText(rs.getString("dd_no"));
           txt_BankName.setText(rs.getString("bank_name"));
           txt_RecievedFrom.setText(rs.getString("student_name"));
           txt_Year1.setText(rs.getString("year1"));
           txt_Year2.setText(rs.getString("year2"));
           DateChooser.setDate(rs.getDate("date"));
           txt_RollNo.setText(rs.getString("roll_no"));
           combo_Stream.setSelectedItem(rs.getString("course_name"));
           txt_Amount.setText(rs.getString("amount"));
           txt_cgst.setText(rs.getString("cgst"));
           txt_sgst.setText(rs.getString("sgst"));
           txt_Total.setText(rs.getString("total_amount"));
           txt_TotalInWord.setText(rs.getString("total_in_words"));
           txt_Remark.setText(rs.getString("remark"));
           
           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btn_Logout = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btn_Home = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        btn_Search = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        btn_Edit = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        btn_CourseList = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        btn_ViewAllRecord = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        btn_Back = new javax.swing.JLabel();
        panelParent = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lbl_ChequeNo = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt_GSTINNo = new javax.swing.JLabel();
        lbl_DDNo = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lbl_BankName = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txt_DDNo = new javax.swing.JTextField();
        combo_PaymentMode = new javax.swing.JComboBox<>();
        txt_RecieptNo = new javax.swing.JTextField();
        txt_ChequeNo = new javax.swing.JTextField();
        txt_BankName = new javax.swing.JTextField();
        DateChooser = new com.toedter.calendar.JDateChooser();
        panelChild = new javax.swing.JPanel();
        txt_TotalInWord = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        combo_Stream = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txt_Year1 = new javax.swing.JTextField();
        txt_StreamName = new javax.swing.JTextField();
        txt_cgst = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        txt_sgst = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txt_Total = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_Remark = new javax.swing.JTextArea();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel18 = new javax.swing.JLabel();
        btn_Print = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txt_RecievedFrom = new javax.swing.JTextField();
        txt_Year2 = new javax.swing.JTextField();
        txt_RollNo = new javax.swing.JTextField();
        txt_Amount = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(0, 51, 51));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 51, 51), java.awt.Color.white, null, null));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_Logout.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 20)); // NOI18N
        btn_Logout.setForeground(new java.awt.Color(255, 255, 255));
        btn_Logout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fee_report/image/my icons/logout.png"))); // NOI18N
        btn_Logout.setText("  Logout");
        btn_Logout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_LogoutMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_LogoutMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_LogoutMouseExited(evt);
            }
        });
        jPanel3.add(btn_Logout, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 210, -1));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 690, 260, 70));

        jPanel4.setBackground(new java.awt.Color(0, 51, 51));
        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_Home.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 20)); // NOI18N
        btn_Home.setForeground(new java.awt.Color(255, 255, 255));
        btn_Home.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fee_report/image/my icons/home.png"))); // NOI18N
        btn_Home.setText("  Home");
        btn_Home.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_HomeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_HomeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_HomeMouseExited(evt);
            }
        });
        jPanel4.add(btn_Home, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 230, -1));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 260, 70));

        jPanel5.setBackground(new java.awt.Color(0, 51, 51));
        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_Search.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 20)); // NOI18N
        btn_Search.setForeground(new java.awt.Color(255, 255, 255));
        btn_Search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fee_report/image/my icons/search2.png"))); // NOI18N
        btn_Search.setText("  Search Record");
        btn_Search.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_SearchMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_SearchMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_SearchMouseExited(evt);
            }
        });
        jPanel5.add(btn_Search, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 220, 60));

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 260, 70));

        jPanel6.setBackground(new java.awt.Color(0, 51, 51));
        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 51, 51), java.awt.Color.white, null, null));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_Edit.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 20)); // NOI18N
        btn_Edit.setForeground(new java.awt.Color(255, 255, 255));
        btn_Edit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fee_report/image/my icons/edit2.png"))); // NOI18N
        btn_Edit.setText("  Edit Courses");
        btn_Edit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_EditMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_EditMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_EditMouseExited(evt);
            }
        });
        jPanel6.add(btn_Edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 230, -1));

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, 260, 70));

        jPanel7.setBackground(new java.awt.Color(0, 51, 51));
        jPanel7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 51, 51), java.awt.Color.white, null, null));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_CourseList.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 20)); // NOI18N
        btn_CourseList.setForeground(new java.awt.Color(255, 255, 255));
        btn_CourseList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fee_report/image/my icons/list.png"))); // NOI18N
        btn_CourseList.setText("  Course List");
        btn_CourseList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_CourseListMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_CourseListMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_CourseListMouseExited(evt);
            }
        });
        jPanel7.add(btn_CourseList, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 210, -1));

        jPanel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, 260, 70));

        jPanel8.setBackground(new java.awt.Color(0, 51, 51));
        jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 51, 51), java.awt.Color.white, null, null));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_ViewAllRecord.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 20)); // NOI18N
        btn_ViewAllRecord.setForeground(new java.awt.Color(255, 255, 255));
        btn_ViewAllRecord.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fee_report/image/my icons/view all record.png"))); // NOI18N
        btn_ViewAllRecord.setText("  View All Record");
        btn_ViewAllRecord.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_ViewAllRecordMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_ViewAllRecordMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_ViewAllRecordMouseExited(evt);
            }
        });
        jPanel8.add(btn_ViewAllRecord, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 240, 70));

        jPanel1.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 480, 260, 70));

        jPanel9.setBackground(new java.awt.Color(0, 51, 51));
        jPanel9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 51, 51), java.awt.Color.white, null, null));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_Back.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 20)); // NOI18N
        btn_Back.setForeground(new java.awt.Color(255, 255, 255));
        btn_Back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fee_report/image/my icons/left-arrow.png"))); // NOI18N
        btn_Back.setText("  Back");
        btn_Back.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_BackMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_BackMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_BackMouseExited(evt);
            }
        });
        jPanel9.add(btn_Back, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 230, -1));

        jPanel1.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 580, 260, 70));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 300, 800));

        panelParent.setBackground(new java.awt.Color(0, 51, 51));
        panelParent.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel10.setBackground(new java.awt.Color(0, 0, 0));
        jPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 102));
        jLabel1.setText("    X");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        jPanel10.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 80, -1));

        panelParent.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 0, 80, 40));

        lbl_ChequeNo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbl_ChequeNo.setForeground(new java.awt.Color(255, 255, 255));
        lbl_ChequeNo.setText("Cheque No :");
        panelParent.add(lbl_ChequeNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Reciept No :   TINT-");
        panelParent.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        txt_GSTINNo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txt_GSTINNo.setForeground(new java.awt.Color(255, 255, 255));
        txt_GSTINNo.setText("GGM400128T");
        panelParent.add(txt_GSTINNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 80, -1, -1));

        lbl_DDNo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbl_DDNo.setForeground(new java.awt.Color(255, 255, 255));
        lbl_DDNo.setText("DD No :");
        panelParent.add(lbl_DDNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, -1, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Mode of payment :");
        panelParent.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, -1, -1));

        lbl_BankName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbl_BankName.setForeground(new java.awt.Color(255, 255, 255));
        lbl_BankName.setText("Bank Name :");
        panelParent.add(lbl_BankName, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, -1, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Date :");
        panelParent.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 30, -1, -1));

        txt_DDNo.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_DDNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_DDNoActionPerformed(evt);
            }
        });
        panelParent.add(txt_DDNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 110, 290, 30));

        combo_PaymentMode.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        combo_PaymentMode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DD", "Cheque", "Cash", "Card" }));
        combo_PaymentMode.setSelectedIndex(2);
        combo_PaymentMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_PaymentModeActionPerformed(evt);
            }
        });
        panelParent.add(combo_PaymentMode, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 70, 290, -1));

        txt_RecieptNo.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_RecieptNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_RecieptNoActionPerformed(evt);
            }
        });
        panelParent.add(txt_RecieptNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 30, 290, 30));

        txt_ChequeNo.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_ChequeNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ChequeNoActionPerformed(evt);
            }
        });
        panelParent.add(txt_ChequeNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 110, 290, 30));

        txt_BankName.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_BankName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_BankNameActionPerformed(evt);
            }
        });
        panelParent.add(txt_BankName, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 150, 290, 30));
        panelParent.add(DateChooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 30, 200, -1));

        panelChild.setBackground(new java.awt.Color(0, 51, 51));
        panelChild.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_TotalInWord.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        txt_TotalInWord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_TotalInWordActionPerformed(evt);
            }
        });
        panelChild.add(txt_TotalInWord, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 370, 410, 40));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("the following payment in the college office for the year :");
        panelChild.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, -1, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Recieved From :");
        panelChild.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, 30));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Receiver Signature");
        panelChild.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 430, -1, -1));

        combo_Stream.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        combo_Stream.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_StreamActionPerformed(evt);
            }
        });
        panelChild.add(combo_Stream, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 100, 290, -1));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Roll No :");
        panelChild.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 100, -1, -1));
        panelChild.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 420, 510, 20));
        panelChild.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 1140, 20));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Stream :");
        panelChild.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, -1, -1));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Remark :");
        panelChild.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 440, -1, -1));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Head");
        panelChild.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 150, -1, -1));

        txt_Year1.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_Year1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_Year1ActionPerformed(evt);
            }
        });
        panelChild.add(txt_Year1, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 60, 90, 30));

        txt_StreamName.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_StreamName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_StreamNameActionPerformed(evt);
            }
        });
        panelChild.add(txt_StreamName, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 190, 290, 30));

        txt_cgst.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_cgst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_cgstActionPerformed(evt);
            }
        });
        panelChild.add(txt_cgst, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 230, 290, 30));
        panelChild.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 1140, 20));

        txt_sgst.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_sgst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_sgstActionPerformed(evt);
            }
        });
        panelChild.add(txt_sgst, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 270, 290, 30));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("SGST 9%");
        panelChild.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 260, -1, -1));

        txt_Total.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_Total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_TotalActionPerformed(evt);
            }
        });
        panelChild.add(txt_Total, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 320, 290, 30));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Total In Words :");
        panelChild.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 380, -1, -1));

        txt_Remark.setColumns(20);
        txt_Remark.setRows(5);
        jScrollPane1.setViewportView(txt_Remark);

        panelChild.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 430, 290, 40));
        panelChild.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 310, 510, 20));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Amount (RS) ");
        panelChild.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 150, -1, -1));

        btn_Print.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btn_Print.setText("Print");
        btn_Print.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_PrintMouseClicked(evt);
            }
        });
        btn_Print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_PrintActionPerformed(evt);
            }
        });
        panelChild.add(btn_Print, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 530, -1, -1));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Serial No. :");
        panelChild.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, -1, -1));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("CGST 9%");
        panelChild.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 230, -1, -1));

        txt_RecievedFrom.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_RecievedFrom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_RecievedFromActionPerformed(evt);
            }
        });
        panelChild.add(txt_RecievedFrom, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, 290, 30));

        txt_Year2.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_Year2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_Year2ActionPerformed(evt);
            }
        });
        panelChild.add(txt_Year2, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 60, 90, 30));

        txt_RollNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_RollNoActionPerformed(evt);
            }
        });
        panelChild.add(txt_RollNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 92, 230, 30));

        txt_Amount.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_AmountMouseClicked(evt);
            }
        });
        txt_Amount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_AmountActionPerformed(evt);
            }
        });
        panelChild.add(txt_Amount, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 190, 290, 30));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("-");
        panelChild.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 60, -1, 30));

        panelParent.add(panelChild, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 1200, 610));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("GSTIN :");
        panelParent.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 80, -1, -1));

        getContentPane().add(panelParent, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 0, 1200, 800));

        setSize(new java.awt.Dimension(1500, 800));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_HomeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_HomeMouseEntered
        Color clr = new Color(0,0,0);
        jPanel4.setBackground(clr);
    }//GEN-LAST:event_btn_HomeMouseEntered

    private void btn_HomeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_HomeMouseExited
        Color clr = new Color(0,51,51);
        jPanel4.setBackground(clr);
    }//GEN-LAST:event_btn_HomeMouseExited

    private void btn_SearchMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_SearchMouseEntered
        Color clr = new Color(0,0,0);
        jPanel5.setBackground(clr);
    }//GEN-LAST:event_btn_SearchMouseEntered

    private void btn_SearchMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_SearchMouseExited
        Color clr = new Color(0,51,51);
        jPanel5.setBackground(clr);
    }//GEN-LAST:event_btn_SearchMouseExited

    private void btn_EditMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_EditMouseEntered
        Color clr = new Color(0,0,0);
        jPanel6.setBackground(clr);
    }//GEN-LAST:event_btn_EditMouseEntered

    private void btn_EditMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_EditMouseExited
        Color clr = new Color(0,51,51);
        jPanel6.setBackground(clr);
    }//GEN-LAST:event_btn_EditMouseExited

    private void btn_CourseListMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_CourseListMouseEntered
       Color clr = new Color(0,0,0);
        jPanel7.setBackground(clr);
    }//GEN-LAST:event_btn_CourseListMouseEntered

    private void btn_CourseListMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_CourseListMouseExited
        Color clr = new Color(0,51,51);
        jPanel7.setBackground(clr);
    }//GEN-LAST:event_btn_CourseListMouseExited

    private void btn_ViewAllRecordMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ViewAllRecordMouseEntered
        Color clr = new Color(0,0,0);
        jPanel8.setBackground(clr);
    }//GEN-LAST:event_btn_ViewAllRecordMouseEntered

    private void btn_ViewAllRecordMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ViewAllRecordMouseExited
        Color clr = new Color(0,51,51);
        jPanel8.setBackground(clr);
    }//GEN-LAST:event_btn_ViewAllRecordMouseExited

    private void btn_BackMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_BackMouseEntered
       Color clr = new Color(0,0,0);
        jPanel9.setBackground(clr);
    }//GEN-LAST:event_btn_BackMouseEntered

    private void btn_BackMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_BackMouseExited
        Color clr = new Color(0,51,51);
        jPanel9.setBackground(clr);
    }//GEN-LAST:event_btn_BackMouseExited

    private void btn_LogoutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_LogoutMouseEntered
        Color clr = new Color(0,0,0);
        jPanel3.setBackground(clr);
    }//GEN-LAST:event_btn_LogoutMouseEntered

    private void btn_LogoutMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_LogoutMouseExited
       Color clr = new Color(0,51,51);
        jPanel3.setBackground(clr);
    }//GEN-LAST:event_btn_LogoutMouseExited

    private void btn_LogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_LogoutMouseClicked
       Login_Page s=new Login_Page();
        s.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_LogoutMouseClicked

    private void btn_HomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_HomeMouseClicked
        Home_Page s = new Home_Page();
        s.show();
        this.dispose();
    }//GEN-LAST:event_btn_HomeMouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        System.exit(0);
    }//GEN-LAST:event_jLabel1MouseClicked

    private void txt_DDNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_DDNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_DDNoActionPerformed

    private void combo_PaymentModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_PaymentModeActionPerformed
        if(combo_PaymentMode.getSelectedIndex()==0){
            lbl_DDNo.setVisible(true);
            lbl_ChequeNo.setVisible(false);
            lbl_BankName.setVisible(true);
            txt_DDNo.setVisible(true);
            txt_ChequeNo.setVisible(false);
            txt_BankName.setVisible(true);
        }
        
        if(combo_PaymentMode.getSelectedIndex()==1){
            lbl_DDNo.setVisible(false);
            lbl_ChequeNo.setVisible(true);
            lbl_BankName.setVisible(true);
            txt_DDNo.setVisible(false);
            txt_ChequeNo.setVisible(true);
            txt_BankName.setVisible(true);
        }
        
        if(combo_PaymentMode.getSelectedIndex()==2){
            lbl_DDNo.setVisible(false);
            lbl_ChequeNo.setVisible(false);
            lbl_BankName.setVisible(false);
            txt_DDNo.setVisible(false);
            txt_ChequeNo.setVisible(false);
            txt_BankName.setVisible(false);
        }
        
        if(combo_PaymentMode.getSelectedIndex()==3){
            lbl_DDNo.setVisible(false);
            lbl_ChequeNo.setVisible(false);
            lbl_BankName.setVisible(true);
            txt_DDNo.setVisible(false);
            txt_ChequeNo.setVisible(false);
            txt_BankName.setVisible(true);
        }
    }//GEN-LAST:event_combo_PaymentModeActionPerformed

    private void txt_RecieptNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_RecieptNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_RecieptNoActionPerformed

    private void txt_TotalInWordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_TotalInWordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_TotalInWordActionPerformed

    private void txt_ChequeNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ChequeNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_ChequeNoActionPerformed

    private void txt_BankNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_BankNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_BankNameActionPerformed

    private void combo_StreamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_StreamActionPerformed
        txt_StreamName.setText(combo_Stream.getSelectedItem().toString());
    }//GEN-LAST:event_combo_StreamActionPerformed

    private void txt_Year1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_Year1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_Year1ActionPerformed

    private void txt_StreamNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_StreamNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_StreamNameActionPerformed

    private void txt_cgstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cgstActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_cgstActionPerformed

    private void txt_sgstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_sgstActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_sgstActionPerformed

    private void txt_TotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_TotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_TotalActionPerformed

    private void btn_PrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_PrintActionPerformed
        if(validation()== true){
            JOptionPane.showMessageDialog(this, "validation");
            String result = updateData();
            if(result.equals("success")){
                JOptionPane.showMessageDialog(this, "record updated successfully");
                Print_Page p = new Print_Page();
                p.setVisible(true);
                this.dispose();
            }else{
                JOptionPane.showMessageDialog(this, "record updationtion failed");
            }
        }
    }//GEN-LAST:event_btn_PrintActionPerformed

    private void txt_RecievedFromActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_RecievedFromActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_RecievedFromActionPerformed

    private void txt_Year2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_Year2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_Year2ActionPerformed

    private void txt_RollNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_RollNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_RollNoActionPerformed

    private void txt_AmountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_AmountActionPerformed
        Float amnt = Float.parseFloat(txt_Amount.getText());
        Float cgst = (float) (amnt * 0.09);
        Float sgst = (float) (amnt * 0.09);
        txt_cgst.setText(cgst.toString());
        txt_sgst.setText(sgst.toString());
        float total = amnt+sgst+cgst;
        txt_Total.setText(Float.toString(total));
        txt_TotalInWord.setText(NumberToWord.convert((int)total)+ " only");
    }//GEN-LAST:event_txt_AmountActionPerformed

    private void txt_AmountMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_AmountMouseClicked
        /*Float amnt = Float.parseFloat(txt_Amount.getText());
        Float cgst = (float) (amnt * 0.09);
        Float sgst = (float) (amnt * 0.09);
        txt_cgst.setText(cgst.toString());
        txt_sgst.setText(sgst.toString());
        float total = amnt+cgst+sgst;
        txt_Total.setText(Float.toString(total));
        txt_TotalInWord.setText(NumberToWord.convert((int)total)+ " only");*/
    }//GEN-LAST:event_txt_AmountMouseClicked

    private void btn_PrintMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_PrintMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_PrintMouseClicked

    private void btn_SearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_SearchMouseClicked
        SearchRecord s=new SearchRecord();
        s.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_SearchMouseClicked

    private void btn_EditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_EditMouseClicked
        EditCourse s=new EditCourse();
        s.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_EditMouseClicked

    private void btn_CourseListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_CourseListMouseClicked
        ViewCourse s=new ViewCourse();
        s.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_CourseListMouseClicked

    private void btn_ViewAllRecordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ViewAllRecordMouseClicked
        ViewAllRecord s=new ViewAllRecord();
        s.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_ViewAllRecordMouseClicked

    private void btn_BackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_BackMouseClicked
        Print_Page s=new Print_Page();
        s.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_BackMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UpdateFeeDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UpdateFeeDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UpdateFeeDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UpdateFeeDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UpdateFeeDetails().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser DateChooser;
    private javax.swing.JLabel btn_Back;
    private javax.swing.JLabel btn_CourseList;
    private javax.swing.JLabel btn_Edit;
    private javax.swing.JLabel btn_Home;
    private javax.swing.JLabel btn_Logout;
    private javax.swing.JButton btn_Print;
    private javax.swing.JLabel btn_Search;
    private javax.swing.JLabel btn_ViewAllRecord;
    private javax.swing.JComboBox<String> combo_PaymentMode;
    private javax.swing.JComboBox<String> combo_Stream;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel lbl_BankName;
    private javax.swing.JLabel lbl_ChequeNo;
    private javax.swing.JLabel lbl_DDNo;
    private javax.swing.JPanel panelChild;
    private javax.swing.JPanel panelParent;
    private javax.swing.JTextField txt_Amount;
    private javax.swing.JTextField txt_BankName;
    private javax.swing.JTextField txt_ChequeNo;
    private javax.swing.JTextField txt_DDNo;
    private javax.swing.JLabel txt_GSTINNo;
    private javax.swing.JTextField txt_RecieptNo;
    private javax.swing.JTextField txt_RecievedFrom;
    private javax.swing.JTextArea txt_Remark;
    private javax.swing.JTextField txt_RollNo;
    private javax.swing.JTextField txt_StreamName;
    private javax.swing.JTextField txt_Total;
    private javax.swing.JTextField txt_TotalInWord;
    private javax.swing.JTextField txt_Year1;
    private javax.swing.JTextField txt_Year2;
    private javax.swing.JTextField txt_cgst;
    private javax.swing.JTextField txt_sgst;
    // End of variables declaration//GEN-END:variables
}
