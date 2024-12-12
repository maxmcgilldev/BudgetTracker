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

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class BudgetBase extends JPanel {    

    // high level UI stuff
    JFrame topLevelFrame;  
    GridBagConstraints layoutConstraints = new GridBagConstraints(); 

    // widgets which may have listeners and/or values
    private JButton calculateButton;   
    private JButton exitButton;        
    private JTextField wagesField;     
    private JTextField loansField;     
    private JTextField otherIncomeField; 
    private JTextField totalIncomeField; 
    
    // spending fields
    private JTextField foodField;      
    private JTextField rentField;      
    private JTextField otherExpenseField; 
    private JTextField totalSpendingField; 
    
    // result field
    private JTextField surplusDeficitField;

    // time period combo boxes
    private JComboBox<String> wagesTimeBox;
    private JComboBox<String> loansTimeBox;
    private JComboBox<String> otherIncomeTimeBox;
    private JComboBox<String> foodTimeBox;
    private JComboBox<String> rentTimeBox;
    private JComboBox<String> otherExpenseTimeBox;

    // conversion constants
    private static final double WEEKS_IN_YEAR = 52.0;
    private static final double MONTHS_IN_YEAR = 12.0;
    private static final double WEEKS_IN_MONTH = 4.3333333;

    public BudgetBase(JFrame frame) {
        topLevelFrame = frame;
        setLayout(new GridBagLayout());
        initComponents();
    }

    private void initComponents() { 
        // Top row (0) - "INCOME" label
        JLabel incomeLabel = new JLabel("INCOME");
        addComponent(incomeLabel, 0, 0, 3, 1);

        // Row 1 - Wages
        JLabel wagesLabel = new JLabel("Wages");
        addComponent(wagesLabel, 1, 0);
        wagesField = createTextField();
        addComponent(wagesField, 1, 1);
        wagesTimeBox = createTimeComboBox();
        addComponent(wagesTimeBox, 1, 2);

        // Row 2 - Loans
        JLabel loansLabel = new JLabel("Loans");
        addComponent(loansLabel, 2, 0);
        loansField = createTextField();
        addComponent(loansField, 2, 1);
        loansTimeBox = createTimeComboBox();
        addComponent(loansTimeBox, 2, 2);

        // Row 3 - Other Income
        JLabel otherIncomeLabel = new JLabel("Other Income");
        addComponent(otherIncomeLabel, 3, 0);
        otherIncomeField = createTextField();
        addComponent(otherIncomeField, 3, 1);
        otherIncomeTimeBox = createTimeComboBox();
        addComponent(otherIncomeTimeBox, 3, 2);

        // Row 4 - Total Income
        JLabel totalIncomeLabel = new JLabel("Total Income (Yearly)");
        addComponent(totalIncomeLabel, 4, 0);
        totalIncomeField = createReadOnlyField();
        addComponent(totalIncomeField, 4, 1, 2, 1);

        // Add spacing
        addComponent(new JLabel(" "), 5, 0);

        // Row 6 - SPENDING label
        JLabel spendingLabel = new JLabel("SPENDING");
        addComponent(spendingLabel, 6, 0, 3, 1);

        // Row 7 - Food
        JLabel foodLabel = new JLabel("Food");
        addComponent(foodLabel, 7, 0);
        foodField = createTextField();
        addComponent(foodField, 7, 1);
        foodTimeBox = createTimeComboBox();
        addComponent(foodTimeBox, 7, 2);

        // Row 8 - Rent
        JLabel rentLabel = new JLabel("Rent");
        addComponent(rentLabel, 8, 0);
        rentField = createTextField();
        addComponent(rentField, 8, 1);
        rentTimeBox = createTimeComboBox();
        addComponent(rentTimeBox, 8, 2);

        // Row 9 - Other Expenses
        JLabel otherExpenseLabel = new JLabel("Other Expenses");
        addComponent(otherExpenseLabel, 9, 0);
        otherExpenseField = createTextField();
        addComponent(otherExpenseField, 9, 1);
        otherExpenseTimeBox = createTimeComboBox();
        addComponent(otherExpenseTimeBox, 9, 2);

        // Row 10 - Total Spending
        JLabel totalSpendingLabel = new JLabel("Total Spending (Yearly)");
        addComponent(totalSpendingLabel, 10, 0);
        totalSpendingField = createReadOnlyField();
        addComponent(totalSpendingField, 10, 1, 2, 1);

        // Add spacing
        addComponent(new JLabel(" "), 11, 0);

        // Row 12 - Surplus/Deficit
        JLabel surplusDeficitLabel = new JLabel("Surplus/Deficit (Yearly)");
        addComponent(surplusDeficitLabel, 12, 0);
        surplusDeficitField = createReadOnlyField();
        addComponent(surplusDeficitField, 12, 1, 2, 1);

        // Buttons
        calculateButton = new JButton("Calculate");
        addComponent(calculateButton, 13, 0, 3, 1);
        exitButton = new JButton("Exit");
        addComponent(exitButton, 14, 0, 3, 1);

        initListeners();
    }

    private JTextField createTextField() {
        JTextField field = new JTextField("", 10);
        field.setHorizontalAlignment(JTextField.RIGHT);
        return field;
    }

    private JTextField createReadOnlyField() {
        JTextField field = createTextField();
        field.setEditable(false);
        return field;
    }

    private JComboBox<String> createTimeComboBox() {
        String[] periods = {"Per Week", "Per Month", "Per Year"};
        JComboBox<String> timeBox = new JComboBox<>(periods);
        timeBox.setSelectedItem("Per Month");  // default to monthly
        return timeBox;
    }

    private void initListeners() {
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        calculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculate();
            }
        });
    }

    private void addComponent(Component component, int gridrow, int gridcol) {
        addComponent(component, gridrow, gridcol, 1, 1);
    }

    private void addComponent(Component component, int gridrow, int gridcol, int gridwidth, int gridheight) {
        layoutConstraints.fill = GridBagConstraints.HORIZONTAL;
        layoutConstraints.gridx = gridcol;
        layoutConstraints.gridy = gridrow;
        layoutConstraints.gridwidth = gridwidth;
        layoutConstraints.gridheight = gridheight;
        add(component, layoutConstraints);
        layoutConstraints.gridwidth = 1;
        layoutConstraints.gridheight = 1;
    }

    private void calculate() {
        double totalIncome = calculateTotalIncome();
        double totalSpending = calculateTotalSpending();
        calculateSurplusDeficit(totalIncome, totalSpending);
    }

    public double calculateTotalIncome() {
        double wages = convertToYearly(getTextFieldValue(wagesField), wagesTimeBox);
        double loans = convertToYearly(getTextFieldValue(loansField), loansTimeBox);
        double otherIncome = convertToYearly(getTextFieldValue(otherIncomeField), otherIncomeTimeBox);

        if (Double.isNaN(wages) || Double.isNaN(loans) || Double.isNaN(otherIncome)) {
            totalIncomeField.setText("");
            return 0.0;
        }

        double totalIncome = wages + loans + otherIncome;
        totalIncomeField.setText(String.format("%.2f", totalIncome));
        return totalIncome;
    }

    private double calculateTotalSpending() {
        double food = convertToYearly(getTextFieldValue(foodField), foodTimeBox);
        double rent = convertToYearly(getTextFieldValue(rentField), rentTimeBox);
        double otherExpense = convertToYearly(getTextFieldValue(otherExpenseField), otherExpenseTimeBox);

        if (Double.isNaN(food) || Double.isNaN(rent) || Double.isNaN(otherExpense)) {
            totalSpendingField.setText("");
            return 0.0;
        }

        double totalSpending = food + rent + otherExpense;
        totalSpendingField.setText(String.format("%.2f", totalSpending));
        return totalSpending;
    }

    private void calculateSurplusDeficit(double income, double spending) {
        double surplus = income - spending;
        surplusDeficitField.setText(String.format("%.2f", surplus));
        surplusDeficitField.setForeground(surplus >= 0 ? Color.BLACK : Color.RED);
    }

    private double convertToYearly(double value, JComboBox<String> timeBox) {
        if (Double.isNaN(value)) return Double.NaN;
        
        String period = (String) timeBox.getSelectedItem();
        switch (period) {
            case "Per Week":
                // Can either go week->month->year or directly week->year
                // Option 1: Using WEEKS_IN_MONTH
                return value * WEEKS_IN_MONTH * MONTHS_IN_YEAR;
                // Option 2: Direct (current way)
                // return value * WEEKS_IN_YEAR;
            case "Per Month":
                return value * MONTHS_IN_YEAR;
            case "Per Year":
                return value;
            default:
                return value;
        }
    }

    private double getTextFieldValue(JTextField field) {
        String fieldString = field.getText();
        if (fieldString.isBlank()) return 0;
        try {
            return Double.parseDouble(fieldString);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(topLevelFrame, "Please enter a valid number");
            return Double.NaN;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Budget Calculator");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setContentPane(new BudgetBase(frame));
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}