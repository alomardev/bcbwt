package ce313.team4.project2015;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * A frame that contains two sections, instruction section and diagram section.
 * Instruction section has the required components to update the diagram section
 * and start tracing. Diagram section consist of the basic computer, control
 * unit and wires.
 *
 * @author Team 4
 */
public class CombinedFrame extends JFrame implements ActionListener,
        DocumentListener {

    // Padding for better layouting
    final static private int PADMAJ = 10;
    final static private int PADMIN = 5;

    // Basic computer diagram panel
    final private BasicComputerPanel bcPanel;

    // Analyser componenets
    final private JLabel lblInstruction, lblDetails, lblACVal;
    final private JButton btnAnalyse;
    final private PJTextField tfInstruction;
    final private JTextArea taDetails;
    final private JScrollPane spDetails;

    // Controller components
    final private JLabel lblTimeTable;
    final private JButton btnStartStop, btnPulse;
    final private JTextArea taTimeTable;
    final private PJTextField tfPulse;
    final private JCheckBox cbAutoAnalysis, cbR, cbIEN, cbFGI, cbFGO,
            cbE, cbZDR, cbAutoPulse;
    final private JRadioButton rbPAC, rbNAC, rbZAC;
    final private JScrollPane spTimeTable;

    // Monospaced font for better readiblity
    final private Font fntMono;

    // Pulse thread pointer that controls the auomatic pulse.
    private Thread pulseThread;

    // Calculation variables
    private int inst = -1, simpleInst, time, maxTime, pulseSec = -1;

    private boolean isStarted = false;

    /**
     * Initializes all components and 'final' variables.
     */
    public CombinedFrame() {
        /* initializing objects */
        bcPanel = new BasicComputerPanel();

        lblInstruction = new JLabel("Instruction:");
        lblDetails = new JLabel("Details:");
        btnAnalyse = new JButton("Analyse");
        cbAutoAnalysis = new JCheckBox("Auto analysis");
        tfInstruction = new PJTextField(10);
        taDetails = new JTextArea(5, 40);
        spDetails = new JScrollPane(taDetails,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        lblTimeTable = new JLabel("Time Table:");
        btnStartStop = new JButton("Start");
        btnPulse = new JButton("Pulse");
        taTimeTable = new JTextArea(9, 53);
        tfPulse = new PJTextField(6);
        cbR = new JCheckBox("R");
        cbIEN = new JCheckBox("IEN");
        cbFGI = new JCheckBox("FGI");
        cbFGO = new JCheckBox("FGO");
        cbE = new JCheckBox("E");
        cbZDR = new JCheckBox("Zero DR");
        lblACVal = new JLabel("AC Register:");
        rbPAC = new JRadioButton("Positive");
        rbNAC = new JRadioButton("Negative");
        rbZAC = new JRadioButton("Zero");
        cbAutoPulse = new JCheckBox("Pulse every (second):");
        spTimeTable = new JScrollPane(taTimeTable,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        fntMono = new Font(Font.MONOSPACED, Font.PLAIN, 12);
    }

    /**
     * Setups components and manages layout.
     */
    public void setup() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Basic Computer Wires Tracer");

        // Used commonly for layouting
        GridBagConstraints gc = new GridBagConstraints();

        // panelValues contains flags and values for instructions execution
        JPanel panelValues = new JPanel();
        JPanel panelTop = new JPanel();
        JPanel panelBottom = new JPanel();

        panelTop.setLayout(new GridLayout(1, 6));
        panelTop.add(cbR, 0);
        panelTop.add(cbIEN, 1);
        panelTop.add(cbFGI, 2);
        panelTop.add(cbFGO, 3);
        panelTop.add(cbE, 4);
        panelTop.add(cbZDR, 5);
        panelTop.setBorder(BorderFactory.createEmptyBorder(0, PADMAJ, 0, PADMAJ));

        panelBottom.setLayout(new GridBagLayout());
        gc.anchor = GridBagConstraints.LINE_END;
        gc.weightx = 1;
        gc.insets.set(0, 0, 0, PADMAJ);
        gc.gridx = 0;
        gc.gridy = 0;
        panelBottom.add(lblACVal, gc);
        gc.insets.set(0, 0, 0, 0);
        gc.anchor = GridBagConstraints.LINE_START;
        gc.gridx = 1;
        panelBottom.add(rbZAC, gc);
        gc.gridx = 2;
        panelBottom.add(rbPAC, gc);
        gc.gridx = 3;
        panelBottom.add(rbNAC, gc);

        panelValues.setLayout(new BoxLayout(panelValues, BoxLayout.Y_AXIS));
        panelValues.add(panelTop);
        panelValues.add(panelBottom);

        panelValues.setBorder(BorderFactory.createTitledBorder(BorderFactory.
                createEtchedBorder(),
                "Required Values", TitledBorder.LEFT, TitledBorder.TOP));

        /* Layouting */
        setLayout(new GridBagLayout());
        gc.anchor = GridBagConstraints.CENTER;

        gc.insets.set(PADMAJ, PADMAJ, PADMAJ, PADMIN);
        gc.gridx = 0;
        gc.gridy = 0;
        gc.gridheight = 8;
        gc.weightx = 500;
        gc.fill = GridBagConstraints.BOTH;
        add(bcPanel, gc);

        gc.anchor = GridBagConstraints.LINE_START;

        gc.insets.set(PADMAJ, PADMAJ + 3, 0, 0);
        gc.gridheight = 1;
        gc.gridx = 1;
        gc.weightx = 0;
        add(lblInstruction, gc);

        gc.insets.set(PADMIN, PADMAJ, 0, 0);
        gc.gridy = 1;
        gc.weightx = 1;
        gc.gridwidth = 2;
        gc.fill = GridBagConstraints.BOTH;
        add(tfInstruction, gc);

        gc.insets.set(PADMIN, PADMAJ, 0, 0);
        gc.weightx = -1;
        gc.gridwidth = 1;
        gc.gridx = 3;
        add(cbAutoAnalysis, gc);

        gc.insets.set(PADMIN, PADMIN, 0, PADMAJ);
        gc.gridx = 4;
        add(btnAnalyse, gc);

        gc.insets.set(PADMAJ, PADMAJ + 3, 0, 0);
        gc.gridx = 1;
        gc.gridy = 2;
        add(lblDetails, gc);

        gc.insets.set(PADMIN, PADMAJ, 0, PADMAJ);
        gc.gridy = 3;
        gc.weightx = 1;
        gc.weighty = .3;
        gc.gridwidth = 4;
        add(spDetails, gc);

        gc.insets.set(PADMAJ, PADMAJ, 0, PADMAJ);
        gc.weighty = 0;
        gc.gridy = 4;
        add(panelValues, gc);

        gc.insets.set(PADMAJ, PADMAJ + 3, 0, 0);
        gc.gridy = 5;
        gc.weightx = 0;
        gc.gridwidth = 1;
        add(lblTimeTable, gc);

        gc.insets.set(PADMIN, PADMAJ, 0, PADMAJ);
        gc.gridy = 6;
        gc.weightx = 1;
        gc.weighty = 1;
        gc.gridwidth = 4;
        add(spTimeTable, gc);

        gc.insets.set(PADMAJ, PADMAJ, PADMAJ, 0);
        gc.gridy = 7;
        gc.weightx = 0;
        gc.weighty = 0;
        gc.gridwidth = 1;
        add(cbAutoPulse, gc);

        gc.insets.set(PADMAJ, PADMIN, PADMAJ, 0);
        gc.gridx = 2;
        add(tfPulse, gc);

        gc.insets.set(PADMAJ, PADMAJ, PADMAJ, 0);
        gc.gridx = 3;
        gc.weightx = 1;
        add(btnPulse, gc);

        gc.insets.set(PADMAJ, PADMIN, PADMAJ, PADMAJ);
        gc.gridx = 4;
        add(btnStartStop, gc);

        /* Setting up components */
        bcPanel.setBackground(Color.WHITE);
        bcPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        taDetails.setLineWrap(true);
        taDetails.setWrapStyleWord(true);
        taDetails.setEditable(false);
        taDetails.setFont(fntMono);
        taDetails.setBorder(BorderFactory.createEmptyBorder(PADMIN, PADMIN,
                PADMIN, PADMIN));

        taTimeTable.setEditable(false);
        taTimeTable.setFont(fntMono);
        taTimeTable.setBorder(BorderFactory.createEmptyBorder(PADMIN, PADMIN,
                PADMIN, PADMIN));

        tfInstruction.setHorizontalAlignment(SwingConstants.CENTER);
        tfInstruction.setFont(fntMono);
        tfInstruction.setPlaceholder("Type four hexs instruction");

        btnAnalyse.setMnemonic('A');
        btnPulse.setMnemonic('P');
        btnStartStop.setMnemonic('S');

        tfPulse.setHorizontalAlignment(SwingConstants.CENTER);
        tfPulse.setFont(fntMono);
        tfPulse.setPlaceholder("1-10");
        tfPulse.setMinimumSize(tfPulse.getPreferredSize());

        ButtonGroup acGroup = new ButtonGroup();
        acGroup.add(rbZAC);
        acGroup.add(rbNAC);
        acGroup.add(rbPAC);
        rbZAC.setSelected(true);

        cbAutoAnalysis.setSelected(false);

        // Measuring best size
        pack();
        setMinimumSize(getSize());

        /* Actions */
        btnAnalyse.addActionListener(this);
        btnPulse.addActionListener(this);
        btnStartStop.addActionListener(this);
        tfInstruction.addActionListener(this);
        cbAutoAnalysis.addActionListener(this);
        cbR.addActionListener(this);
        cbAutoPulse.addActionListener(this);
        // For auto analysis
        tfInstruction.getDocument().addDocumentListener(this);

        updateComponents();
    }

    /**
     * Updates time table with two spaces (padding) at the beginning of each
     * line. The two spaces are reserved for marking the current time on the
     * table text area.
     *
     * @param instruction
     */
    private void setTimeTable(int instruction) {
        maxTime = InstructionUtils.getTimeLimit(inst, cbR.isSelected());
        bcPanel.setInstruction(simpleInst, maxTime);

        String text = InstructionUtils.getTimeTable(instruction,
                cbR.isSelected());


        /* Add two spaces at the beginning of each line */
        StringBuilder sb = new StringBuilder();

        Pattern pattern = Pattern.compile("^(.*)+(\\n)?", Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            sb.append("  ");
            sb.append(matcher.group());
        }

        // Setting modified text to the table area
        taTimeTable.setText(sb.toString());
        taTimeTable.setCaretPosition(0);
    }

    /**
     * Search for the corresponding time in the time table text area and mark it
     * with an arrow. If the time not present in the table, nothing will be
     * marked.
     */
    private void updateTableAndWires() {
        taTimeTable.setText(taTimeTable.getText().replace(">", " "));

        taTimeTable.setText(taTimeTable.getText().replace("  T" + time,
                "> T" + time));

        int caretIndex = taTimeTable.getText().indexOf(">");
        taTimeTable.setCaretPosition(caretIndex > 0 ? caretIndex : 0);

        bcPanel.updateWires(time, cbR.isSelected(), cbIEN.isSelected(),
                cbFGI.isSelected(), cbFGO.isSelected(), cbE.isSelected(),
                cbZDR.isSelected(), rbZAC.isSelected(), rbPAC.isSelected(),
                rbNAC.isSelected());

        bcPanel.pulse(pulseSec > 0 ? pulseSec * 500 : 750);
    }

    /**
     * Update the look of components regarding the current state of the
     * application; e.g. disable all components if the tracing is started.
     */
    private void updateComponents() {
        boolean validInst = inst != -1;
        boolean r = cbR.isSelected();
        if (isStarted) {
            btnStartStop.setText("Stop");
        } else {
            btnStartStop.setText("Start");
        }
        tfInstruction.setEnabled(!isStarted);
        btnAnalyse.setEnabled(!isStarted && !cbAutoAnalysis.isSelected());
        cbAutoAnalysis.setEnabled(!isStarted);
        cbR.setEnabled(validInst && !isStarted);
        cbIEN.setEnabled(!isStarted && validInst && !r);
        cbFGI.setEnabled(!isStarted && validInst && !r);
        cbFGO.setEnabled(!isStarted && validInst && !r);
        cbE.setEnabled(!isStarted && validInst && !r && (inst == 0x7002
                || inst == 0x7100));
        cbZDR.setEnabled(!isStarted && validInst && !r
                && simpleInst == 0x6000 || simpleInst == 0xE000);
        lblACVal.setEnabled(!isStarted && validInst && !r
                && (inst == 0x7010 || inst == 0x7008 || inst == 0x7004));
        rbZAC.setEnabled(lblACVal.isEnabled());
        rbPAC.setEnabled(lblACVal.isEnabled());
        rbNAC.setEnabled(lblACVal.isEnabled());
        tfPulse.setEnabled(cbAutoPulse.isSelected() && !isStarted);
        cbAutoPulse.setEnabled(!isStarted && validInst);
        btnPulse.setEnabled(validInst && !cbAutoPulse.isSelected()
                && isStarted);
        btnStartStop.setEnabled(validInst);
    }

    /**
     * Step to the next time and generate a pulse.
     */
    private void nextTime() {
        time++;
        if (time > maxTime) {
            time = 0;
        }
        bcPanel.pulse(pulseSec > 0 ? pulseSec * 500 : 750);
        updateTableAndWires();
    }

    /**
     * Analyzes the the entered instruction if it is valid.
     */
    private void analysInstruction() {
        String instText = tfInstruction.getText();
        if (InstructionUtils.isValid(instText)) {
            cbR.setSelected(false);
            inst = Integer.parseInt(instText, 16);
            simpleInst = InstructionUtils.simplify(inst);
            taDetails.setText(InstructionUtils.getDetails(inst));
            setTimeTable(inst);
        } else {
            taDetails.setText("Invalid instruction!");
            taTimeTable.setText("");
            inst = -1;
        }
        updateComponents();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAnalyse || e.getSource() == tfInstruction) {
            analysInstruction();
        } else if (e.getSource() == cbAutoAnalysis) {
            updateComponents();
        } else if (e.getSource() == cbR) {
            if (inst != -1) {
                setTimeTable(inst);
                updateComponents();
            }
        } else if (e.getSource() == cbAutoPulse) {
            tfPulse.setEnabled(cbAutoPulse.isSelected());
        } else if (e.getSource() == btnStartStop) {
            boolean validSeconds = true;
            if (cbAutoPulse.isSelected()) {
                try {
                    pulseSec = Integer.parseInt(tfPulse.getText());
                } catch (NumberFormatException ex) {
                    System.out.println(ex.getMessage());
                    validSeconds = false;
                }
                validSeconds = validSeconds && pulseSec > 0 && pulseSec <= 10;
            } else {
                pulseSec = -1;
            }
            if (validSeconds) {
                if (!isStarted) {
                    isStarted = true;
                    time = 0;
                    updateTableAndWires();

                    if (cbAutoPulse.isSelected()) {
                        pulseThread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    while (isStarted) {
                                        Thread.sleep(pulseSec * 1000);
                                        if (isStarted) {
                                            nextTime();
                                        }
                                    }
                                } catch (InterruptedException ex) {
                                    System.out.println(ex.getMessage());
                                }
                                System.out.println("thread finished.");
                            }
                        });
                        pulseThread.start();
                    }
                } else {
                    isStarted = false;
                    time = -1;
                    taTimeTable.setText(taTimeTable.getText().replace(">", " "));
                    bcPanel.resetWires();

                    if (pulseThread != null) {
                        pulseThread.interrupt();
                        pulseThread = null;
                    }
                }
                updateComponents();
            } else {
                JOptionPane.showMessageDialog(this, "Please enter the delay"
                        + " between each\npulse in seconds (ranged from 1 to"
                        + " 10).", "Invalid Seconds",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == btnPulse) {
            nextTime();
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        if (cbAutoAnalysis.isSelected()) {
            analysInstruction();
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        if (cbAutoAnalysis.isSelected()) {
            analysInstruction();
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    }
}
