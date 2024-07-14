package com.houarizegai.calculator.ui;
import com.houarizegai.calculator.logic.CalculatorLogic;
import com.houarizegai.calculator.theme.ThemeLoader;
import com.houarizegai.calculator.theme.properties.Theme;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.Map;
import static com.houarizegai.calculator.util.ColorUtil.hex2Color;

public class CalculatorUI {
    private static final String FONT_NAME = "Comic Sans MS";
    private static final String APPLICATION_TITLE = "Calculator";
    private static final int WINDOW_WIDTH = 410;
    private static final int WINDOW_HEIGHT = 600;
    private static final int BUTTON_WIDTH = 80;
    private static final int BUTTON_HEIGHT = 70;
    private static final int MARGIN_X = 20;
    private static final int MARGIN_Y = 60;
    private static final int BASE = 100;
    private static final int INCREMENT = 80;
    private static final int BUTTON_COUNT = 22;
    private static final int COLUMN_COUNT = 5;
    private static final int ROW_COUNT = 6;
    private static final int COLUMN_WIDTH = 90;
    private static final int THEME_SELECTOR_WIDTH = 230;
    private static final int THEME_SELECTOR_HEIGHT = 30;
    private static final int C_TYPE_W = 20;
    private static final int C_TYPE_H = 30;
    private static final int SCIEN_WIN_W_INC = 80;
    private static final int NO_0 = 0;
    private static final int NO_1 = 1;
    private static final int NO_2 = 2;
    private static final int NO_3 = 3;
    private static final int NO_4 = 4;
    private static final int NO_5 = 5;
    private static final int NO_6 = 6;
    private static final int NO_7 = 7;
    private static final int NO_8 = 8;
    private static final int NO_9 = 9;
    private static final int NO_10 = 10;
    private static final int NO_11 = 11;
    private static final int NO_12 = 12;
    private static final int NO_13 = 13;
    private static final int NO_14 = 14;
    private static final int NO_15 = 15;
    private static final int NO_16 = 16;
    private static final int NO_17 = 17;
    private static final int NO_18 = 18;
    private static final int NO_19 = 19;
    private static final int NO_20 = 20;
    private static final int NO_21 = 21;
    private final JFrame window;
    private JComboBox<String> cCType;
    private JComboBox<String> comboTheme;
    private JTextField inputScreen;
    private final JButton[] buttons;
    private final CalculatorLogic calculatorLogic;
    private final Map<String, Theme> themesMap;
    public CalculatorUI() {
        calculatorLogic = new CalculatorLogic();
        themesMap = ThemeLoader.loadThemes();

        window = new JFrame(APPLICATION_TITLE);
        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        window.setLocationRelativeTo(null);

        int[] columns = new int[COLUMN_COUNT];
        for (int i = 0; i < columns.length; i++) {
            columns[i] = MARGIN_X + COLUMN_WIDTH * i;
        }

        int[] rows = new int[ROW_COUNT];
        for (int i = 0; i < rows.length; i++) {
            if (i == 0) {
                rows[i] = MARGIN_Y;
            } else {
                rows[i] = MARGIN_Y + BASE + INCREMENT * (i - 1);
            }
        }

        buttons = new JButton[BUTTON_COUNT];

        initInputScreen(columns, rows);
        initButtons(columns, rows);
        initCalculatorTypeSelector();
        initThemeSelector();

        window.setLayout(null);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }

    private void initThemeSelector() {
        comboTheme = cCBox(themesMap.keySet().toArray(new String[0]), THEME_SELECTOR_WIDTH, THEME_SELECTOR_HEIGHT, "Theme");
        comboTheme.addItemListener(event -> {
            if (event.getStateChange() != ItemEvent.SELECTED) return;
            String selectedTheme = (String) event.getItem();
            applyTheme(themesMap.get(selectedTheme));
        });

        themesMap.values().stream().findFirst().ifPresent(this::applyTheme);
    }

    private void initInputScreen(int[] columns, int[] rows) {
        int cols = 0,r = 0; int w = 350,h =70; int fontSize = 33;
        inputScreen = new JTextField("0");
        inputScreen.setBounds(columns[cols], rows[r], w, h);
        inputScreen.setEditable(false);
        inputScreen.setBackground(Color.WHITE);
        inputScreen.setFont(new Font(FONT_NAME, Font.PLAIN, fontSize));
        window.add(inputScreen);
    }

    private void initCalculatorTypeSelector() {
        cCType = cCBox(new String[]{"Standard", "Scientific"}, C_TYPE_W,C_TYPE_H, "Calculator type");
        cCType.addItemListener(event -> {
            if (event.getStateChange() != ItemEvent.SELECTED) return;

            String selectedItem = (String) event.getItem();
            switch (selectedItem) {
                case "Standard":
                    window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
                    toggleScientificButtons(false);
                    break;
                case "Scientific":
                    window.setSize(WINDOW_WIDTH + SCIEN_WIN_W_INC, WINDOW_HEIGHT);
                    toggleScientificButtons(true);
                    break;
            }
        });
    }

    private void initButtons(int[] columns, int[] rows) {
        buttons[NO_0] = createButton("C", columns[NO_0], rows[NO_1], event -> clearInput());
        buttons[NO_1] = createButton("<-", columns[NO_1], rows[NO_1], event -> backspace());
        buttons[NO_2] = createButton("%", columns[NO_2], rows[NO_1], event -> handleOperator('%'));
        buttons[NO_3] = createButton("/", columns[NO_3], rows[NO_1], event -> handleOperator('/'));
        buttons[NO_4] = createButton("7", columns[NO_0], rows[NO_2], event -> appendDigit("7"));
        buttons[NO_5] = createButton("8", columns[NO_1], rows[NO_2], event -> appendDigit("8"));
        buttons[NO_6] = createButton("9", columns[NO_2], rows[NO_2], event -> appendDigit("9"));
        buttons[NO_7] = createButton("*", columns[NO_3], rows[NO_2], event -> handleOperator('*'));
        buttons[NO_8] = createButton("4", columns[NO_0], rows[NO_3], event -> appendDigit("4"));
        buttons[NO_9] = createButton("5", columns[NO_1], rows[NO_3], event -> appendDigit("5"));
        buttons[NO_10] = createButton("6", columns[NO_2], rows[NO_3], event -> appendDigit("6"));
        buttons[NO_11] = createButton("-", columns[NO_3], rows[NO_3], event -> handleOperator('-'));
        buttons[NO_12] = createButton("1", columns[NO_0], rows[NO_4], event -> appendDigit("1"));
        buttons[NO_13] = createButton("2", columns[NO_1], rows[NO_4], event -> appendDigit("2"));
        buttons[NO_14] = createButton("3", columns[NO_2], rows[NO_4], event -> appendDigit("3"));
        buttons[NO_15] = createButton("+", columns[NO_3], rows[NO_4], event -> handleOperator('+'));
        buttons[NO_16] = createButton(".", columns[NO_0], rows[NO_5], event -> appendDecimalPoint());
        buttons[NO_17] = createButton("0", columns[NO_1], rows[NO_5], event -> appendDigit("0"));
        buttons[NO_18] = createButton("=", columns[NO_2], rows[NO_5], event -> evaluate());
        buttons[NO_19] = createButton(new ButtonParams("√", columns[NO_4], rows[NO_1], event -> handleOperator('√'), false));
        buttons[NO_20] = createButton(new ButtonParams("pow", columns[NO_4], rows[NO_2], event -> handleOperator('^'), false));
        buttons[NO_21] = createButton(new ButtonParams("ln", columns[NO_4], rows[NO_3], event -> handleOperator('l'), false));
    }

    private JComboBox<String> cCBox(String[] items, int x, int y, String toolTip) {
        JComboBox<String> combo = new JComboBox<>(items);
        int width = 140;
        int height = 25;
        combo.setBounds(x, y, width, height);
        combo.setToolTipText(toolTip);
        combo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        window.add(combo);
        return combo;
    }

    private JButton createButton(String label, int x, int y, java.awt.event.ActionListener actionListener) {
        return createButton(new ButtonParams(label, x, y, actionListener, true));
    }


    private JButton createButton(ButtonParams params) {
        JButton btn = new JButton(params.label);
        int size = 28;
        btn.setBounds(params.x, params.y, BUTTON_WIDTH, BUTTON_HEIGHT);
        btn.setFont(new Font(FONT_NAME, Font.PLAIN, size));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addActionListener(params.actionListener);
        btn.setVisible(params.isVisible);
        window.add(btn);
        return btn;
    }

    private void applyTheme(Theme theme) {
        Color textColor = hex2Color(theme.getTextColor());
        Color backgroundColor = hex2Color(theme.getApplicationBackground());
        Color buttonBackgroundColor = hex2Color(theme.getBtnEqualBackground());
        Color buttonForegroundColor = hex2Color(theme.getBtnEqualTextColor());

        inputScreen.setForeground(textColor);
        inputScreen.setBackground(backgroundColor);
        window.getContentPane().setBackground(backgroundColor);

        for (JButton button : buttons) {
            button.setForeground(buttonForegroundColor);
            button.setBackground(buttonBackgroundColor);
        }
    }

    private void toggleScientificButtons(boolean visible) {
        int start = 19, end = 22;
        for (int i = start; i < end; i++) {
            buttons[i].setVisible(visible);
        }
    }

    private void backspace() {
        String input = inputScreen.getText();
        if (input.length() > 1) {
            inputScreen.setText(input.substring(0, input.length() - 1));
        } else {
            inputScreen.setText("0");
        }
    }

    private void clearInput() {
        calculatorLogic.clearInput();
        inputScreen.setText("0");
    }

    private void handleOperator(char operator) {
        calculatorLogic.handleOperator(inputScreen.getText(), operator);
    }

    private void evaluate() {
        try {
            double result = calculatorLogic.evaluate(inputScreen.getText());
            inputScreen.setText(String.valueOf(result));
        } catch (NumberFormatException e) {
            inputScreen.setText("Error");
        }
    }
    private void appendDigit(String digit) {
        if (calculatorLogic.isAddToDisplay()) {
            inputScreen.setText(inputScreen.getText() + digit);
        } else {
            inputScreen.setText(digit);
            calculatorLogic.setAddToDisplay(true);
        }
    }

    private void appendDecimalPoint() {
        if (calculatorLogic.isAddToDisplay() && !inputScreen.getText().contains(".")) {
            inputScreen.setText(inputScreen.getText() + ".");
        } else if (!calculatorLogic.isAddToDisplay()) {
            inputScreen.setText("0.");
            calculatorLogic.setAddToDisplay(true);
        }
    }
}
