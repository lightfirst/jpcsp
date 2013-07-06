/*
 This file is part of jpcsp.

 Jpcsp is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 Jpcsp is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with Jpcsp.  If not, see <http://www.gnu.org/licenses/>.
 */
package jpcsp.Debugger;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

import jpcsp.Emulator;
import jpcsp.Memory;
import jpcsp.settings.Settings;
import jpcsp.util.Utilities;

/**
 *
 * @author George
 */
public class MemoryViewer extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;
    private int visiblelines = 0;
    private int startaddress;
    private Point lastLocation = null;

    public MemoryViewer() {
        //this.cpu = c;
        startaddress = Emulator.getProcessor().cpu.pc;
        initComponents();
        RefreshMemory();
    }

    public static char converttochar(int character) {
        if (character < 0x020 || character >= 0x07f && character <= 0x0a0 || character == 0x0ad) {
            return '.';
        }
        return (char) (character & 0x0ff);
    }

    private static byte safeRead8(Memory mem, int address) {
        byte value = 0;
        if (Memory.isAddressGood(address)) {
            value = (byte) mem.read8(address);
        }
        return value;
    }

    public static String getMemoryView(int addr) {
        byte[] line = new byte[16];
        Memory mem = Memory.getInstance();

        for (int i = 0; i < line.length; i++) {
            line[i] = safeRead8(mem, addr + i);
        }

        return String.format("%08x : %02x %02x %02x %02x %02x %02x "
                + "%02x %02x %02x %02x %02x %02x %02x %02x "
                + "%02x %02x %c %c %c %c %c %c %c %c %c %c %c %c %c %c %c %c", addr,
                line[0], line[1], line[2], line[3], line[4], line[5], line[6], line[7],
                line[8], line[9], line[10], line[11], line[12], line[13], line[14], line[15],
                converttochar(line[0]), converttochar(line[1]),
                converttochar(line[2]), converttochar(line[3]),
                converttochar(line[4]), converttochar(line[5]),
                converttochar(line[6]), converttochar(line[7]),
                converttochar(line[8]), converttochar(line[9]),
                converttochar(line[10]), converttochar(line[11]),
                converttochar(line[12]), converttochar(line[13]),
                converttochar(line[14]), converttochar(line[15]));
    }

    public void RefreshMemory() {
        int addr = startaddress;
        taMemoryView.setText(""); // NOI18N

        visiblelines = taMemoryView.getHeight() / taMemoryView.getFontMetrics(taMemoryView.getFont()).getHeight();
        for (int y = 0; y < visiblelines; y++) {
            if (y > 0) {
                taMemoryView.append("\n");
            }
            taMemoryView.append(getMemoryView(addr));
            addr += 16;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        AddressField = new javax.swing.JTextField();
        btnGoToAddress = new javax.swing.JButton();
        btnGoToSP = new javax.swing.JButton();
        btnDumpRawRam = new javax.swing.JButton();
        btnGoToVRAM = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        taMemoryView = new javax.swing.JTextArea();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("jpcsp/languages/jpcsp"); // NOI18N
        setTitle(bundle.getString("MemoryViewer.title")); // NOI18N
        setMinimumSize(new java.awt.Dimension(800, 380));
        setPreferredSize(new java.awt.Dimension(800, 380));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowDeactivated(java.awt.event.WindowEvent evt) {
                formWindowDeactivated(evt);
            }
        });

        AddressField.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        AddressField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        AddressField.setText("0x00000000"); // NOI18N
        AddressField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                onKeyPressed(evt);
            }
        });

        btnGoToAddress.setText(bundle.getString("MemoryViewer.btnGoToAddress.text")); // NOI18N
        btnGoToAddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGoToAddressActionPerformed(evt);
            }
        });

        btnGoToSP.setText(bundle.getString("MemoryViewer.btnGoToSP.text")); // NOI18N
        btnGoToSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGoToSPActionPerformed(evt);
            }
        });

        btnDumpRawRam.setText(bundle.getString("MemoryViewer.btnDumpRawRam.text")); // NOI18N
        btnDumpRawRam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDumpRawRamActionPerformed(evt);
            }
        });

        btnGoToVRAM.setText(bundle.getString("MemoryViewer.btnGoToVRAM.text")); // NOI18N
        btnGoToVRAM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGoToVRAMActionPerformed(evt);
            }
        });

        jPanel1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jPanel1ComponentResized(evt);
            }
        });

        taMemoryView.setEditable(false);
        taMemoryView.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        taMemoryView.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        taMemoryView.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                taMemoryViewMouseWheelMoved(evt);
            }
        });
        taMemoryView.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                taMemoryViewKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(taMemoryView)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(taMemoryView, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(AddressField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGoToAddress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGoToVRAM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGoToSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnDumpRawRam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDumpRawRam)
                    .addComponent(AddressField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGoToAddress)
                    .addComponent(btnGoToSP)
                    .addComponent(btnGoToVRAM))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void taMemoryViewKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_taMemoryViewKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_DOWN) {
            startaddress += 16;
            evt.consume();
            RefreshMemory();
        } else if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_UP) {
            startaddress -= 16;
            evt.consume();
            RefreshMemory();
        } else if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_PAGE_UP) {
            startaddress -= 16 * visiblelines;
            evt.consume();
            RefreshMemory();
        } else if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_PAGE_DOWN) {
            startaddress += 16 * visiblelines;
            evt.consume();
            RefreshMemory();
        }
}//GEN-LAST:event_taMemoryViewKeyPressed

private void btnGoToAddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGoToAddressActionPerformed
        GoToAddress();
}//GEN-LAST:event_btnGoToAddressActionPerformed

    private void GoToAddress() {
        String gettext = AddressField.getText();
        int value;
        try {
            value = Integer.decode(gettext);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("jpcsp/languages/jpcsp").getString("MemoryViewer.strInvalidAddress.text"));
            return;
        }
        startaddress = value;
        AddressField.setText(String.format("0x%08x", value));
        RefreshMemory();
    }

private void taMemoryViewMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_taMemoryViewMouseWheelMoved
        if (evt.getWheelRotation() > 0) {
            startaddress += 16;
            evt.consume();
            RefreshMemory();
        } else {
            startaddress -= 16;
            evt.consume();
            RefreshMemory();
        }
}//GEN-LAST:event_taMemoryViewMouseWheelMoved

private void formWindowDeactivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowDeactivated
        // called when the mainWindow is closed
        if (Settings.getInstance().readBool("gui.saveWindowPos")) {
            Point location = getLocation();
            if (lastLocation == null || location.x != lastLocation.x || location.y != lastLocation.y) {
                Settings.getInstance().writeWindowPos("memoryview", location);
                lastLocation = location;
            }
        }
}//GEN-LAST:event_formWindowDeactivated

private void btnGoToSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGoToSPActionPerformed
        startaddress = Emulator.getProcessor().cpu._sp;
        RefreshMemory();
}//GEN-LAST:event_btnGoToSPActionPerformed

private void btnDumpRawRamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDumpRawRamActionPerformed
        File f = new File("ramdump.bin"); // NOI18N
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter(f));
            Memory mem = Memory.getInstance();
            for (int i = 0x08000000; i <= 0x09ffffff; i++) {
                out.write(safeRead8(mem, i));
            }
        } catch (IOException e) {
            // do nothing
        } finally {
            Utilities.close(out);
        }
}//GEN-LAST:event_btnDumpRawRamActionPerformed

private void onKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_onKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            GoToAddress();
        }
}//GEN-LAST:event_onKeyPressed

private void btnGoToVRAMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGoToVRAMActionPerformed
        startaddress = 0x04000000;
        RefreshMemory();
}//GEN-LAST:event_btnGoToVRAMActionPerformed

    private void jPanel1ComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel1ComponentResized
        // this is needed to override the size of the TextArea with text already present
        taMemoryView.setMinimumSize(jPanel1.getMinimumSize());
        taMemoryView.setMaximumSize(jPanel1.getMaximumSize());
        RefreshMemory();
    }//GEN-LAST:event_jPanel1ComponentResized

    @Override
    public void dispose() {
        Emulator.getMainGUI().endWindowDialog();
        super.dispose();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField AddressField;
    private javax.swing.JButton btnDumpRawRam;
    private javax.swing.JButton btnGoToAddress;
    private javax.swing.JButton btnGoToSP;
    private javax.swing.JButton btnGoToVRAM;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextArea taMemoryView;
    // End of variables declaration//GEN-END:variables
}
