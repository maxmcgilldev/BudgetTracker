// base code for student budget assessment
// Students do not need to use this code in their assessment, fine to junk it and do something different!
//
// Your submission must be a maven project, and must be submitted via Codio, and run in Codio
//
// user can enter in wages and loans and calculate total income
//
// run in Codio 
// To see GUI, run with java and select Box Url from Codio top line menu
//
// Layout - Uses GridBag layout in a straightforward way, every component has a (column, row) position in the UI grid
// Not the prettiest layout, but relatively straightforward
// Students who use IntelliJ or Eclipse may want to use the UI designers in these IDEs , instead of GridBagLayout
package Budget;

// Swing imports
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

// class definition
public class BudgetBase extends JPanel {    // based on Swing JPanel

    // high level UI stuff
    JFrame topLevelFrame;  // top-level JFrame
    GridBagConstraints layoutConstraints = new GridBagConstraints(); // used to control layout

    // widgets which may have listeners and/or values
    private JButton calculateButton;   // Calculate button
    private JButton exitButton;        // Exit button
    private JTextField wagesField;     // Wages text field
    private JTextField loansField;     // Loans text field
    private JTextField otherIncomeField; // Other income text field
    private JTextField totalIncomeField; // Total Income field
    
    // spending fields
    private JTextField foodField;      // Food text field
    private JTextField rentField;      // Rent text field
    private JTextField otherExpenseField; // Other expenses text field
    private JTextField totalSpendingField; // Total spending field
    
    // result field
    private JTextField surplusDeficitField; // Surplus/deficit field

    // constructor - create UI  (dont need to change this)
    public BudgetBase(JFrame frame) {
        topLevelFrame = frame; // keep track of top-level frame
        setLayout(new GridBagLayout());  // use GridBag layout
        initComponents();  // initalise components
    }

    // initialise componenents
    private void initComponents() { 

        // Top row (0) - "INCOME" label
        JLabel incomeLabel = new JLabel("INCOME");
        addComponent(incomeLabel, 0, 0);

        // Row 1 - Wages label followed by wages textbox
        JLabel wagesLabel = new JLabel("Wages");
        addComponent(wagesLabel, 1, 0);

        // set up text field for entering wages
        wagesField = new JTextField("", 10);   // blank initially, with 10 columns
        wagesField.setHorizontalAlignment(JTextField.RIGHT);    // number is at right end of field
        addComponent(wagesField, 1, 1);   

        // Row 2 - Loans label followed by loans textbox
        JLabel loansLabel = new JLabel("Loans");
        addComponent(loansLabel, 2, 0);

        // set up text box for entering loans
        loansField = new JTextField("", 10);   // blank initially, with 10 columns
        loansField.setHorizontalAlignment(JTextField.RIGHT);    // number is at right end of field
        addComponent(loansField, 2, 1); 

        // Row 3 - Other Income label and field
        JLabel otherIncomeLabel = new JLabel("Other Income");
        addComponent(otherIncomeLabel, 3, 0);

        otherIncomeField = new JTextField("", 10);
        otherIncomeField.setHorizontalAlignment(JTextField.RIGHT);
        addComponent(otherIncomeField, 3, 1);

        // Row 4 - Total Income label followed by total income field
        JLabel totalIncomeLabel = new JLabel("Total Income");
        addComponent(totalIncomeLabel, 4, 0);

        // set up text box for displaying total income.  Users can view, but cannot directly edit it
        totalIncomeField = new JTextField("0", 10);   // 0 initially, with 10 columns
        totalIncomeField.setHorizontalAlignment(JTextField.RIGHT);    // number is at right end of field
        totalIncomeField.setEditable(false);    // user cannot directly edit this field (ie, it is read-only)
        addComponent(totalIncomeField, 4, 1);  

        // Row 5 - SPENDING label
        JLabel spendingLabel = new JLabel("SPENDING");
        addComponent(spendingLabel, 5, 0);

        // Row 6 - Food expenses
        JLabel foodLabel = new JLabel("Food");
        addComponent(foodLabel, 6, 0);
        foodField = new JTextField("", 10);
        foodField.setHorizontalAlignment(JTextField.RIGHT);
        addComponent(foodField, 6, 1);

        // Row 7 - Rent expenses
        JLabel rentLabel = new JLabel("Rent");
        addComponent(rentLabel, 7, 0);
        rentField = new JTextField("", 10);
        rentField.setHorizontalAlignment(JTextField.RIGHT);
        addComponent(rentField, 7, 1);

        // Row 8 - Other expenses
        JLabel otherExpenseLabel = new JLabel("Other Expenses");
        addComponent(otherExpenseLabel, 8, 0);
        otherExpenseField = new JTextField("", 10);
        otherExpenseField.setHorizontalAlignment(JTextField.RIGHT);
        addComponent(otherExpenseField, 8, 1);

        // Row 9 - Total Spending
        JLabel totalSpendingLabel = new JLabel("Total Spending");
        addComponent(totalSpendingLabel, 9, 0);
        totalSpendingField = new JTextField("0", 10);
        totalSpendingField.setHorizontalAlignment(JTextField.RIGHT);
        totalSpendingField.setEditable(false);
        addComponent(totalSpendingField, 9, 1);

        // Row 10 - Surplus/Deficit
        JLabel surplusDeficitLabel = new JLabel("Surplus/Deficit");
        addComponent(surplusDeficitLabel, 10, 0);
        surplusDeficitField = new JTextField("0", 10);
        surplusDeficitField.setHorizontalAlignment(JTextField.RIGHT);
        surplusDeficitField.setEditable(false);
        addComponent(surplusDeficitField, 10, 1);

        // Row 11 - Calculate Button
        calculateButton = new JButton("Calculate");
        addComponent(calculateButton, 11, 0);  

        // Row 12 - Exit Button
        exitButton = new JButton("Exit");
        addComponent(exitButton, 12, 0);  

        // set up listeners (in a separate method)
        initListeners();
    }

    // set up listeners
    private void initListeners() {
        // exitButton - exit program when pressed
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // calculateButton - call calculate() when pressed
        calculateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculate();
            }
        });
    }

    // add a component at specified row and column in UI
    private void addComponent(Component component, int gridrow, int gridcol) {
        layoutConstraints.fill = GridBagConstraints.HORIZONTAL;
        layoutConstraints.gridx = gridcol;
        layoutConstraints.gridy = gridrow;
        add(component, layoutConstraints);
    }

    // calculate everything when Calculate button is pressed
    private void calculate() {
        double totalIncome = calculateTotalIncome();
        double totalSpending = calculateTotalSpending();
        calculateSurplusDeficit(totalIncome, totalSpending);
    }

    // calculate total income
    public double calculateTotalIncome() {
        double wages = getTextFieldValue(wagesField);
        double loans = getTextFieldValue(loansField);
        double otherIncome = getTextFieldValue(otherIncomeField);

        if (Double.isNaN(wages) || Double.isNaN(loans) || Double.isNaN(otherIncome)) {
            totalIncomeField.setText("");
            return 0.0;
        }

        double totalIncome = wages + loans + otherIncome;
        totalIncomeField.setText(String.format("%.2f", totalIncome));
        return totalIncome;
    }

    // calculate total spending
    private double calculateTotalSpending() {
        double food = getTextFieldValue(foodField);
        double rent = getTextFieldValue(rentField);
        double otherExpense = getTextFieldValue(otherExpenseField);

        if (Double.isNaN(food) || Double.isNaN(rent) || Double.isNaN(otherExpense)) {
            totalSpendingField.setText("");
            return 0.0;
        }

        double totalSpending = food + rent + otherExpense;
        totalSpendingField.setText(String.format("%.2f", totalSpending));
        return totalSpending;
    }

    // calculate and display surplus/deficit
    private void calculateSurplusDeficit(double income, double spending) {
        double surplus = income - spending;
        surplusDeficitField.setText(String.format("%.2f", surplus));
        surplusDeficitField.setForeground(surplus >= 0 ? Color.BLACK : Color.RED);
    }

    // return the value if a text field as a double
    // --return 0 if field is blank
    // --return NaN if field is not a number
    private double getTextFieldValue(JTextField field) {
        String fieldString = field.getText();  // get text from text field

        if (fieldString.isBlank()) {   // if text field is blank, return 0
            return 0;
        }

        else {  // if text field is not blank, parse it into a double
            try {
                return Double.parseDouble(fieldString);  // parse field number into a double
             } catch (java.lang.NumberFormatException ex) {  // catch invalid number exception
                JOptionPane.showMessageDialog(topLevelFrame, "Please enter a valid number");  // show error message
                return Double.NaN;  // return NaN to show that field is not a number
            }
        }
    }

    // standard method to show UI
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Budget Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Create and set up the content pane.
        BudgetBase newContentPane = new BudgetBase(frame);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
 
    // standard main class to set up Swing UI
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(); 
            }
        });
    }
}