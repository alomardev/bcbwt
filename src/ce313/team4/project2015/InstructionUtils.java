package ce313.team4.project2015;

/**
 * Utility class for manipulation and analysis of instructions.
 *
 * @author Team 4
 */
public class InstructionUtils {

    /**
     * Set of instructions that our basic computer can handle
     */
    final public static int[] INST_SET = new int[]{0x0000, 0x8000, 0x1000,
        0x9000, 0x2000, 0xA000, 0x3000, 0xB000, 0x4000, 0xC000, 0x5000, 0xD000,
        0x6000, 0xE000, 0x7800, 0x7400, 0x7200, 0x7100, 0x7080, 0x7040, 0x7020,
        0x7010, 0x7008, 0x7004, 0x7002, 0x7001, 0xF800, 0xF400, 0xF200, 0xF100,
        0xF080, 0xF040};

    /**
     * Checks whether the given string can be parsed to as a hex integer or not.
     *
     * @param string the string to be checked
     * @return true if the string is a hex integer
     */
    public static boolean isHexInteger(String string) {
        if (string.isEmpty()) {
            return false;
        }
        string = string.toUpperCase();
        for (int c = 0; c < string.length(); c++) {
            char cc = string.charAt(c);
            if (cc != '0' && cc != '1' && cc != '2' && cc != '3' && cc != '4'
                    && cc != '5' && cc != '6' && cc != '7' && cc != '8'
                    && cc != '9' && cc != 'A' && cc != 'B' && cc != 'C'
                    && cc != 'D' && cc != 'E' && cc != 'F') {
                return false;
            }
        }
        return true;
    }

    /**
     * Simplify the given instruction by removing the address part for memory
     * reference instructions.
     *
     * @param instruction original instruction
     * @return simplified instruction
     */
    public static int simplify(int instruction) {
        int firstHex = instruction >> 12;
        if (firstHex == 0x0 || firstHex == 0x8
                || firstHex == 0x1 || firstHex == 0x9
                || firstHex == 0x2 || firstHex == 0xA
                || firstHex == 0x3 || firstHex == 0xB
                || firstHex == 0x4 || firstHex == 0xC
                || firstHex == 0x5 || firstHex == 0xD
                || firstHex == 0x6 || firstHex == 0xE) {
            return firstHex << 12;
        }
        return instruction;
    }

    /**
     * Check whether our basic computer can handle the given instruction or not.
     *
     * @param instruction
     * @return true if it is valid
     */
    public static boolean isValid(String instruction) {
        if (instruction.length() != 4) {
            return false;
        }
        if (!isHexInteger(instruction)) {
            return false;
        }
        int instValue = Integer.parseInt(instruction, 16);
        int d = instValue >> 12 & 0b111;
        boolean validInst = false;
        for (int inst : INST_SET) {
            if (d < 7) {
                if (instValue >> 12 == inst >> 12) {
                    validInst = true;
                }
            } else if (instValue == inst) {
                validInst = true;
            }
        }
        return validInst;
    }

    /**
     * There is a time limit for each instruction. It actually returns the time
     * where the instruction reset (or clear) the SC register.
     *
     * @param instruction
     * @param r interrupt cycle flag
     * @return the time of clearing SC
     */
    public static int getTimeLimit(int instruction, boolean r) {
        if (r) {
            return 2;
        }

        int d = instruction >> 12 & 0b111;
        if (d >= 7) {
            return 3;
        } else {
            switch (d) {
                case 0:
                case 1:
                case 2:
                case 5:
                    return 5;
                case 3:
                case 4:
                    return 4;
                case 6:
                    return 6;
            }
        }
        return -1;
    }

    /**
     * Brings detailed information about the given instruction. Hexadecimal
     * code, symbol, and description are constructed.
     *
     * @param instruction
     * @return detailed information or "Invalid instruction" if the given
     * instruction is not valid
     */
    public static String getDetails(int instruction) {
        String message = new String();

        // direct/indirect bit
        boolean i = instruction >> 15 == 1;
        // opcode bits
        int d = instruction >> 12 & 0b111;

        // Replace address part with Xs
        if (d == 0 && !i) {
            message = "Code: 0XXX\n";
        } else {
            message += "Code: "
                    + Integer.toHexString(instruction).toUpperCase() + "\n";
            if (d < 7) {
                message = "Code: " + message.charAt(6) + "XXX\n";
            }
        }

        // Appending instruction type
        message += "Type: ";
        if (d < 7) {
            message += "Memory Reference";
            if (i) {
                message += " (indirect)";
            }
            message += "\n";
        } else if (!i) {
            message += "Register Reference\n";
        } else {
            message += "Input Output\n";
        }

        // Appending instruction symbol and description
        switch (d) {
            case 0:
                message += "Symbol: AND\n";
                message += "Description: AND memory word to AC";
                return message;
            case 1:
                message += "Symbol: ADD\n";
                message += "Description: Add memory word to AC";
                return message;
            case 2:
                message += "Symbol: LDA\n";
                message += "Description: Load memory word to AC";
                return message;
            case 3:
                message += "Symbol: STA\n";
                message += "Description: Store content of AC in memory";
                return message;
            case 4:
                message += "Symbol: BUN\n";
                message += "Description: Branch unconditionally";
                return message;
            case 5:
                message += "Symbol: BSA\n";
                message += "Description: Branch and save return address";
                return message;
            case 6:
                message += "Symbol: ISZ\n";
                message += "Description: Increment and skip if zero";
                return message;
        }
        if (instruction == 0x7800) {
            message += "Symbol: CLA\n";
            message += "Description: Clear AC";
            return message;
        }
        if (instruction == 0x7400) {
            message += "Symbol: CLE\n";
            message += "Description: Clear E";
            return message;
        }
        if (instruction == 0x7200) {
            message += "Symbol: CMA\n";
            message += "Description: Complement AC";
            return message;
        }
        if (instruction == 0x7100) {
            message += "Symbol: CME\n";
            message += "Description: Complement E";
            return message;
        }
        if (instruction == 0x7080) {
            message += "Symbol: CIR\n";
            message += "Description: Circulate right AC";
            return message;
        }
        if (instruction == 0x7040) {
            message += "Symbol: CIL\n";
            message += "Description: Circulate left AC";
            return message;
        }
        if (instruction == 0x7020) {
            message += "Symbol: INC\n";
            message += "Description: Increment AC";
            return message;
        }
        if (instruction == 0x7010) {
            message += "Symbol: SPA\n";
            message += "Description: Skip next instruction if AC positive";
            return message;
        }
        if (instruction == 0x7008) {
            message += "Symbol: SNA\n";
            message += "Description: Skip next instruction if AC negative";
            return message;
        }
        if (instruction == 0x7004) {
            message += "Symbol: SZA\n";
            message += "Description: Skip next instruction if AC zero";
            return message;
        }
        if (instruction == 0x7002) {
            message += "Symbol: SZE\n";
            message += "Description: Skip next instruction if E is 0";
            return message;
        }
        if (instruction == 0x7001) {
            message += "Symbol: HLT\n";
            message += "Description: Halt computer";
            return message;
        }
        if (instruction == 0xf800) {
            message += "Symbol: INP\n";
            message += "Description: Input character to AC";
            return message;
        }
        if (instruction == 0xf400) {
            message += "Symbol: OUT\n";
            message += "Description: Output character from AC";
            return message;
        }
        if (instruction == 0xf200) {
            message += "Symbol: SKI\n";
            message += "Description: Skip on input flag";
            return message;
        }
        if (instruction == 0xf100) {
            message += "Symbol: SKO\n";
            message += "Description: Skip on output flag";
            return message;
        }
        if (instruction == 0xf080) {
            message += "Symbol: ION\n";
            message += "Description: Interrupt on";
            return message;
        }
        if (instruction == 0xf040) {
            message += "Symbol: IOF\n";
            message += "Description: Interrupt off";
            return message;
        }
        return "Invalid instruction";
    }

    /**
     * Generate time table for the given instruction.
     *
     * @param instruction
     * @param r interrupt cycle flag
     * @return organized time table string or "Invalid instruction" if the
     * instruction is not valid
     */
    public static String getTimeTable(int instruction, boolean r) {
        String message = new String();

        // direct/indirect bit
        boolean i = instruction >> 15 == 1;
        // opcode
        int d = instruction >> 12 & 0b111;

        if (!r) { // Fetch and decode
            message += "T0: AR ← PC\n";
            message += "T1: IR ← M[AR],\n    PC ← PC + 1\n";
            message += "T2: AR ← IR(0-11),\n    I ← IR(15),\n    DECODE IR(12-14)\n";
        } else { // Interrupt cycle
            message += "T0: AR ← 0,\n    TR ← PC\n";
            message += "T1: M[AR] ← TR,\n    PC ← 0\n";
            message += "T2: PC ← PC + 1,\n    IEN ← 0,\n    R ← 0,\n    SC ← 0";
            return message;
        }

        // Detect interrupt
        message += "T3: IF(IEN = 1 AND (FGI = 1 OR FGO = 1)) R ← 1";

        if (d < 7) { // Memory reference instruction
            if (i) { // Indirect instruction
                message += ",\n    AR ← M[AR]\n";
            } else { // Direct instruction
                message += ",\n    NOTHING\n";
            }

            switch (d) {
                case 0: // AND
                    message += "T4: DR ← M[AR]\n";
                    message += "T5: AC ← AC ^ DR,\n    SC ← 0";
                    return message;
                case 1: // ADD
                    message += "T4: DR ← M[AR]\n";
                    message += "T5: AC ← AC + DR,\n    E ← Cout,\n    SC ← 0";
                    return message;
                case 2: // LDA
                    message += "T4: DR ← M[AR]\n";
                    message += "T5: AC ← DR,\n    SC ← 0";
                    return message;
                case 3: // STA
                    message += "T4: M[AR] ← AC,\n    SC ← 0";
                    return message;
                case 4: // BUN
                    message += "T4: PC ← AR,\n    SC ← 0";
                    return message;
                case 5: // BSA
                    message += "T4: M[AR] ← PC,\n    AR ← AR + 1\n";
                    message += "T5: PC ← AR,\n    SC ← 0";
                    return message;
                case 6: // ISZ
                    message += "T4: DR ← M[AR]\n";
                    message += "T5: DR ← DR + 1\n";
                    message += "T6: M[AR] ← DR";
                    message += ",\n    IF(DR = 0) PC ← PC + 1";
                    message += ",\n    SC ← 0";
                    return message;
            }
        } else if (!i) { // Register reference instruction
            switch (instruction) {
                case 0x7800: // CLA
                    message += ",\n    AC ← 0,\n    SC ← 0";
                    return message;
                case 0x7400: // CLE
                    message += ",\n    E ← 0,\n    SC ← 0";
                    return message;
                case 0x7200: // CMA
                    message += ",\n    AC ← AC',\n    SC ← 0";
                    return message;
                case 0x7100: // CME
                    message += ",\n    E ← E',\n    SC ← 0";
                    return message;
                case 0x7080: // CIR
                    message += ",\n    AC ← SHR AC,\n    AC(15) ← E,\n    E ← AC(0),\n    SC ← 0";
                    return message;
                case 0x7040: // CIL
                    message += ",\n    AC ← SHL AC,\n    AC(0) ← E,\n    E ← AC(15),\n    SC ← 0";
                    return message;
                case 0x7020: // INC
                    message += ",\n    AC ← AC + 1,\n    SC ← 0";
                    return message;
                case 0x7010: // SPA
                    message += ",\n    IF(AC(15) = 0) PC ← PC + 1,\n    SC ← 0";
                    return message;
                case 0x7008: // SNA
                    message += ",\n    IF(AC(15) = 1) PC ← PC + 1,\n    SC ← 0";
                    return message;
                case 0x7004: // SZA
                    message += ",\n    IF(AC = 0) PC ← PC + 1,\n    SC ← 0";
                    return message;
                case 0x7002: // SZE
                    message += ",\n    IF(E = 0) PC ← PC + 1,\n    SC ← 0";
                    return message;
                case 0x7001: // HLT
                    message += ",\n    S ← 0\n";
                    return message;
            }
        } else { // Input output instruction
            switch (instruction) {
                case 0xF800: // INP
                    message += ",\n    AC(0-7) ← INPR,\n    FGI ← 0,\n    SC ← 0";
                    return message;
                case 0xF400: // OUT
                    message += ",\n    OUTR ← AC(0-7),\n    FGO ← 0,\n    SC ← 0";
                    return message;
                case 0xF200: // SKI
                    message += ",\n    IF(FGI = 1) PC ← PC + 1,\n    SC ← 0";
                    return message;
                case 0xF100: // SKO
                    message += ",\n    IF(FGO = 1) PC ← PC + 1,\n    SC ← 0";
                    return message;
                case 0xF080: // ION
                    message += ",\n    IEN ← 1,\n    SC ← 0";
                    return message;
                case 0xF040: // IOF
                    message += ",\n    IEN ← 0,\n    SC ← 0";
                    return message;
            }
        }
        return "Invalid instruction";
    }
}
