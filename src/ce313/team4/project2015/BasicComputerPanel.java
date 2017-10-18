package ce313.team4.project2015;

import ce313.team4.project2015.res.R;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * This class shows a basic computer diagram with organize the wires. It has
 * required functionality to display and color the wires in the diagram.
 *
 * @author Team 4
 */
public class BasicComputerPanel extends JPanel {

    final private Color wireColorOne, wireColorZero,
            wireColorGroup, wireColorUnknown, wireColorNone;

    private BufferedImage bcBlocks, bcLabels;
    private HashMap<String, Wire> wires;

    private int simpleInst, d, maxTime;
    private boolean i;

    /**
     * Initializes colors and images for drawing them.
     */
    public BasicComputerPanel() {
        wireColorOne = new Color(0, 175, 0);
        wireColorZero = new Color(255, 150, 150);
        wireColorGroup = new Color(50, 50, 230);
        wireColorUnknown = new Color(200, 175, 0);
        wireColorNone = Color.LIGHT_GRAY;
        wires = new HashMap<>();
        try {
            wires.put("unused", new Wire("unused.png", 89, 50));
            wires.put("pulse", new Wire("pulse.png", 100, 17));
            wires.put("bus_in_out", new Wire("bus_in_out.png", 30, 176));
            wires.put("bus_s0", new Wire("bus_s0.png", 480, 186));
            wires.put("bus_s1", new Wire("bus_s1.png", 480, 176));
            wires.put("bus_s2", new Wire("bus_s2.png", 480, 166));
            wires.put("t0", new Wire("t0.png", 19, 50));
            wires.put("t1", new Wire("t1.png", 29, 50));
            wires.put("t2", new Wire("t2.png", 39, 50));
            wires.put("t3", new Wire("t3.png", 49, 50));
            wires.put("t4", new Wire("t4.png", 59, 50));
            wires.put("t5", new Wire("t5.png", 69, 50));
            wires.put("t6", new Wire("t6.png", 79, 50));
            wires.put("encoder_in_none", new Wire("encoder_in_none.png", 370, 79));
            wires.put("encoder_in_ar", new Wire("encoder_in_ar.png", 370, 89));
            wires.put("encoder_in_pc", new Wire("encoder_in_pc.png", 370, 99));
            wires.put("encoder_in_dr", new Wire("encoder_in_dr.png", 370, 109));
            wires.put("encoder_in_ac", new Wire("encoder_in_ac.png", 370, 119));
            wires.put("encoder_in_ir", new Wire("encoder_in_ir.png", 370, 129));
            wires.put("encoder_in_tr", new Wire("encoder_in_tr.png", 370, 139));
            wires.put("encoder_in_memory", new Wire("encoder_in_memory.png", 370, 147));
            wires.put("tr_inr", new Wire("tr_inr.png", 209, 190));
            wires.put("ac_inr", new Wire("ac_inr.png", 219, 190));
            wires.put("dr_inr", new Wire("dr_inr.png", 229, 190));
            wires.put("pc_inr", new Wire("pc_inr.png", 239, 190));
            wires.put("ar_inr", new Wire("ar_inr.png", 249, 190));
            wires.put("tr_clr", new Wire("tr_clr.png", 269, 190));
            wires.put("ac_clr", new Wire("ac_clr.png", 279, 190));
            wires.put("dr_clr", new Wire("dr_clr.png", 289, 190));
            wires.put("pc_clr", new Wire("pc_clr.png", 299, 190));
            wires.put("ar_clr", new Wire("ar_clr.png", 309, 190));
            wires.put("outr_ld", new Wire("outr_ld.png", 126, 190));
            wires.put("tr_ld", new Wire("tr_ld.png", 139, 190));
            wires.put("ir_ld", new Wire("ir_ld.png", 149, 190));
            wires.put("ac_ld", new Wire("ac_ld.png", 159, 190));
            wires.put("dr_ld", new Wire("dr_ld.png", 169, 190));
            wires.put("pc_ld", new Wire("pc_ld.png", 179, 190));
            wires.put("ar_ld", new Wire("ar_ld.png", 189, 190));
            wires.put("s_out", new Wire("s_out.png", 346, 50));
            wires.put("s_in", new Wire("s_in.png", 336, 50));
            wires.put("i_out", new Wire("i_out.png", 306, 50));
            wires.put("i_in", new Wire("i_in.png", 296, 50));
            wires.put("fgo_out", new Wire("fgo_out.png", 266, 50));
            wires.put("fgo_in", new Wire("fgo_in.png", 256, 50));
            wires.put("fgi_out", new Wire("fgi_out.png", 226, 50));
            wires.put("fgi_in", new Wire("fgi_in.png", 216, 50));
            wires.put("ien_out", new Wire("ien_out.png", 186, 50));
            wires.put("ien_in", new Wire("ien_in.png", 176, 50));
            wires.put("r_out", new Wire("r_out.png", 146, 50));
            wires.put("r_in", new Wire("r_in.png", 136, 50));
            wires.put("timing_clr", new Wire("timing_clr.png", 100, 26));
            wires.put("timing_inr", new Wire("timing_inr.png", 100, 36));
            wires.put("memory_wr", new Wire("memory_wr.png", 329, 190));
            wires.put("inpr_to_alu", new Wire("inpr_to_alu.png", 39, 476));
            wires.put("alu_to_ac", new Wire("alu_to_ac.png", 100, 466));
            wires.put("alu_to_e", new Wire("alu_to_e.png", 76, 440));
            // wires.put("control_to_e", new Wire("control_to_e.png", 49, 169));
            wires.put("e_out", new Wire("e_out.png", 39, 156));
            wires.put("alu_op", new Wire("alu_op.png", 76, 190));

            bcBlocks = ImageIO.read(R.get("blocks.png"));
            bcLabels = ImageIO.read(R.get("labels.png"));

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Sets the instruction and time limit to color wires accordingly.
     *
     * @param instruction
     * @param timeLimit
     */
    public void setInstruction(int instruction, int timeLimit) {
        simpleInst = instruction;
        i = simpleInst >> 15 == 1;
        d = simpleInst >> 12 & 0b111;
        maxTime = timeLimit;
    }

    /**
     * Resets wires color and then redraw the wires on the panel.
     */
    public void resetWires() {
        for (Wire w : wires.values()) {
            w.tint(Color.BLACK);
        }
        repaint();
    }

    /**
     * Colors pulse wire temporarily with one color; then reset it to zero color
     * after a while.
     *
     * @param delay the delay before coloring the wire with zero.
     */
    public void pulse(int delay) {
        wires.get("pulse").tint(wireColorOne);
        repaint();

        Timer resetColor = new Timer();
        resetColor.schedule(new TimerTask() {
            @Override
            public void run() {
                wires.get("pulse").tint(wireColorZero);
                repaint();
            }
        }, delay);
    }

    /**
     * Updates the color of each wire regarding the instruction, and the given
     * parameters. After that, redraw the wires on the panel.
     *
     * @param time the time of the given instruction
     * @param r the flag R (true means one, false means zero).
     * @param ien the flag IEN
     * @param fgi the flag FGO
     * @param fgo the flag FGI
     * @param e the flag E
     * @param zeroDR true means DR value equals zero
     * @param zeroAC true means AC value equals zero
     * @param positiveAC true means AC is greater than zero
     * @param negativeAC true means AC is less than zero
     */
    public void updateWires(int time, boolean r, boolean ien, boolean fgi,
            boolean fgo, boolean e, boolean zeroDR, boolean zeroAC,
            boolean positiveAC, boolean negativeAC) {
        // busIn selects one of the bus inputs;
        // e.g. if it is equals to 3 it will select DR as an input.
        int busIn = 0;
        
        // Recheck R if the time >= 3.
        // This will ensure that the wire is still colored after the time 3.
        if (time >= 3) {
            r = ien && (fgi || fgo);
        }

        // Assign default color to all wires
        for (Wire w : wires.values()) {
            w.tint(wireColorZero);
        }

        /* Initial colors for specific wires */
        wires.get("bus_in_out").tint(wireColorGroup);
        wires.get("alu_to_ac").tint(wireColorGroup);
        wires.get("alu_op").tint(wireColorZero);

        wires.get("unused").tint(wireColorNone);
        wires.get("inpr_to_alu").tint(wireColorNone);
        wires.get("s_in").tint(wireColorNone);
        wires.get("r_in").tint(wireColorNone);
        wires.get("ien_in").tint(wireColorNone);
        wires.get("fgo_in").tint(wireColorNone);
        wires.get("fgi_in").tint(wireColorNone);
        wires.get("i_in").tint(wireColorNone);
        wires.get("alu_to_e").tint(wireColorNone);

        wires.get("r_out").tint(r ? wireColorOne : wireColorZero);
        wires.get("ien_out").tint(ien ? wireColorOne : wireColorZero);
        wires.get("fgo_out").tint(fgo ? wireColorOne : wireColorZero);
        wires.get("fgi_out").tint(fgi ? wireColorOne : wireColorZero);
        wires.get("e_out").tint(e ? wireColorOne : wireColorZero);
        wires.get("i_out").tint(time > 2 && i ? wireColorOne : wireColorZero);

        wires.get("s_out").tint(wireColorOne);
        wires.get("t" + time).tint(wireColorOne);

        wires.get("timing_clr").tint(maxTime == time
                ? wireColorOne : wireColorZero);
        wires.get("timing_inr").tint(maxTime == time
                ? wireColorZero : wireColorOne);

        /* Common for all instructions */
        
        if (r) { // Interrupt cycle
            switch (time) {
                case 0:
                    wires.get("ar_clr").tint(wireColorOne);
                    busIn = 2;
                    wires.get("tr_ld").tint(wireColorOne);
                    break;
                case 1:
                    wires.get("memory_wr").tint(wireColorOne);
                    busIn = 6;
                    wires.get("pc_clr").tint(wireColorOne);
                    break;
                case 2:
                    wires.get("pc_inr").tint(wireColorOne);
                    wires.get("ien_in").tint(wireColorZero);
                    wires.get("ien_out").tint(wireColorZero);
                    wires.get("r_in").tint(wireColorZero);
                    wires.get("r_out").tint(wireColorZero);
                    break;
            }
        } else { // Fetch and decode
            switch (time) {
                case 0:
                    busIn = 2;
                    wires.get("ar_ld").tint(wireColorOne);
                    break;
                case 1:
                    busIn = 7;
                    wires.get("pc_inr").tint(wireColorOne);
                    wires.get("ir_ld").tint(wireColorOne);
                    break;
                case 2:
                    busIn = 5;
                    wires.get("ar_ld").tint(wireColorOne);
                    wires.get("i_in").tint(i ? wireColorOne : wireColorZero);
                    wires.get("i_out").tint(i ? wireColorOne : wireColorZero);
                    break;
            }
        }

        if (time == 3) {
            // Color R wires
            if (r) {
                wires.get("r_in").tint(wireColorOne);
                wires.get("r_out").tint(wireColorOne);
            }

            /* Instruction-specific coloring */
            
            if (d <= 6 && i) { // Indirect access
                busIn = 7;
                wires.get("ar_ld").tint(wireColorOne);
            }

            switch (simpleInst) { // Reg, IO
                case 0x7800:
                    wires.get("ac_clr").tint(wireColorOne);
                    break;
                case 0x7400:
                    wires.get("alu_to_e").tint(wireColorZero);
                    wires.get("e_out").tint(wireColorZero);
                    break;
                case 0x7200:
                    wires.get("ac_ld").tint(wireColorOne);
                    wires.get("alu_op").tint(wireColorGroup);
                    break;
                case 0x7100:
                    wires.get("alu_to_e").tint(e
                            ? wireColorZero : wireColorOne);
                    wires.get("e_out").tint(e
                            ? wireColorZero : wireColorOne);
                    break;
                case 0x7080:
                    wires.get("ac_ld").tint(wireColorOne);
                    wires.get("alu_to_e").tint(wireColorUnknown);
                    wires.get("e_out").tint(wireColorUnknown);
                    wires.get("alu_op").tint(wireColorGroup);
                    break;
                case 0x7040:
                    wires.get("ac_ld").tint(wireColorOne);
                    wires.get("alu_to_e").tint(wireColorUnknown);
                    wires.get("e_out").tint(wireColorUnknown);
                    wires.get("alu_op").tint(wireColorGroup);
                    break;
                case 0x7020:
                    wires.get("ac_inr").tint(wireColorOne);
                    break;
                case 0x7010:
                    if (positiveAC) {
                        wires.get("pc_inr").tint(wireColorOne);
                    }
                    break;
                case 0x7008:
                    if (negativeAC) {
                        wires.get("pc_inr").tint(wireColorOne);
                    }
                    break;
                case 0x7004:
                    if (zeroAC) {
                        wires.get("pc_inr").tint(wireColorOne);
                    }
                    break;
                case 0x7002:
                    if (!e) {
                        wires.get("pc_inr").tint(wireColorOne);
                    }
                    break;
                case 0x7001:
                    wires.get("s_in").tint(wireColorZero);
                    wires.get("s_out").tint(wireColorZero);
                    break;
                case 0xF800:
                    wires.get("inpr_to_alu").tint(wireColorGroup);
                    wires.get("fgi_out").tint(wireColorZero);
                    wires.get("fgi_in").tint(wireColorZero);
                    break;
                case 0xF400:
                    wires.get("outr_ld").tint(wireColorOne);
                    wires.get("fgo_out").tint(wireColorZero);
                    wires.get("fgo_in").tint(wireColorZero);
                    break;
                case 0xF200:
                    if (fgi) {
                        wires.get("pc_inr").tint(wireColorOne);
                    }
                    break;
                case 0xF100:
                    if (fgo) {
                        wires.get("pc_inr").tint(wireColorOne);
                    }
                    break;
                case 0xF080:
                    wires.get("ien_out").tint(wireColorOne);
                    wires.get("ien_in").tint(wireColorOne);
                    break;
                case 0xF040:
                    break;
            }
        }

        /* Memory reference instructions */
        // Seperated from the above condition for less coding.
        switch (d) {
            case 0:
                switch (time) {
                    case 4:
                        busIn = 7;
                        wires.get("dr_ld").tint(wireColorOne);
                        break;
                    case 5:
                        wires.get("ac_ld").tint(wireColorOne);
                        wires.get("alu_op").tint(wireColorGroup);
                        break;
                }
                break;
            case 1:
                switch (time) {
                    case 4:
                        busIn = 7;
                        wires.get("dr_ld").tint(wireColorOne);
                        break;
                    case 5:
                        wires.get("ac_ld").tint(wireColorOne);
                        wires.get("alu_to_e").tint(wireColorUnknown);
                        wires.get("e_out").tint(wireColorUnknown);
                        wires.get("alu_op").tint(wireColorGroup);
                        break;
                }
                break;
            case 2:
                switch (time) {
                    case 4:
                        busIn = 7;
                        wires.get("dr_ld").tint(wireColorOne);
                        break;
                    case 5:
                        wires.get("ac_ld").tint(wireColorOne);
                        wires.get("alu_op").tint(wireColorGroup);
                        break;
                }
                break;
            case 3:
                if (time == 4) {
                    wires.get("memory_wr").tint(wireColorOne);
                    busIn = 4;
                }
                break;
            case 4:
                if (time == 4) {
                    wires.get("pc_ld").tint(wireColorOne);
                    busIn = 1;
                }
                break;
            case 5:
                switch (time) {
                    case 4:
                        wires.get("memory_wr").tint(wireColorOne);
                        busIn = 2;
                        wires.get("ar_inr").tint(wireColorOne);
                        break;
                    case 5:
                        wires.get("pc_ld").tint(wireColorOne);
                        busIn = 1;
                        break;
                }
                break;
            case 6:
                switch (time) {
                    case 4:
                        wires.get("dr_ld").tint(wireColorOne);
                        busIn = 7;
                        break;
                    case 5:
                        wires.get("dr_inr").tint(wireColorOne);
                        break;
                    case 6:
                        wires.get("memory_wr").tint(wireColorOne);
                        busIn = 3;
                        if (zeroDR) {
                            wires.get("pc_inr").tint(wireColorOne);
                        }
                        break;
                }
        }
        
        // Coloring bus-in wires
        wires.get("bus_s0").tint((busIn & 0b1) == 1
                ? wireColorOne : wireColorZero);
        wires.get("bus_s1").tint((busIn >> 1 & 0b1) == 1
                ? wireColorOne : wireColorZero);
        wires.get("bus_s2").tint((busIn >> 2 & 0b1) == 1
                ? wireColorOne : wireColorZero);
        wires.get("encoder_in_none").tint(busIn == 0
                ? wireColorOne : wireColorZero);
        wires.get("encoder_in_memory").tint(busIn == 7
                ? wireColorOne : wireColorZero);
        wires.get("encoder_in_ar").tint(busIn == 1
                ? wireColorOne : wireColorZero);
        wires.get("encoder_in_pc").tint(busIn == 2
                ? wireColorOne : wireColorZero);
        wires.get("encoder_in_dr").tint(busIn == 3
                ? wireColorOne : wireColorZero);
        wires.get("encoder_in_ac").tint(busIn == 4
                ? wireColorOne : wireColorZero);
        wires.get("encoder_in_ir").tint(busIn == 5
                ? wireColorOne : wireColorZero);
        wires.get("encoder_in_tr").tint(busIn == 6
                ? wireColorOne : wireColorZero);

        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(bcBlocks.getWidth(), bcBlocks.getHeight());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        /* Drawing colors note */
        
        int x = (getWidth() - getPreferredSize().width) / 2
                + bcBlocks.getWidth() - 80;
        int y = (getHeight() - getPreferredSize().height) / 2 + 40;

        g.setColor(wireColorNone);
        g.fillOval(x, y, 15, 15);
        g.drawString("NONE", x + 20, y + 12);

        g.setColor(wireColorZero);
        g.fillOval(x, y + 20, 15, 15);
        g.drawString("Zero", x + 20, y + 32);

        g.setColor(wireColorOne);
        g.fillOval(x, y + 40, 15, 15);
        g.drawString("One", x + 20, y + 52);

        g.setColor(wireColorGroup);
        g.fillOval(x, y + 60, 15, 15);
        g.drawString("Bits set", x + 20, y + 72);

        g.setColor(wireColorUnknown);
        g.fillOval(x, y + 80, 15, 15);
        g.drawString("Unknown", x + 20, y + 92);

        /* Drawing images */
        
        g.drawImage(bcBlocks, (getWidth() - getPreferredSize().width) / 2,
                (getHeight() - getPreferredSize().height) / 2, this);
        for (Wire w : wires.values()) {
            w.draw(g, this);
        }
        g.drawImage(bcLabels, (getWidth() - getPreferredSize().width) / 2,
                (getHeight() - getPreferredSize().height) / 2, this);
    }
}
