/*
 * ATLauncher - https://github.com/ATLauncher/ATLauncher
 * Copyright (C) 2013-2022 ATLauncher
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.atlauncher.gui.tabs.instances;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Pattern;

import javax.swing.*;

import com.atlauncher.App;
import com.atlauncher.Launcher;
import com.atlauncher.data.InstanceLauncher;
import com.atlauncher.gui.LauncherFrame;
import com.atlauncher.gui.tabs.InstancesTab;
import com.atlauncher.network.Analytics;

public final class InstancesSearchField extends JPasswordField implements KeyListener {
    private final InstancesTab parent;

    public InstancesSearchField(final InstancesTab parent) {
        super(16);
        this.parent = parent;
        if (!InstanceLauncher.sethEditMode) {
            this.setEchoChar('*');
        }else {
            this.setEchoChar((char)0);
        }
        this.setMaximumSize(new Dimension(190, 23));
        this.addKeyListener(this);

    }

    public Pattern getSearchPattern() {
        return Pattern.compile(Pattern.quote(this.getText()), Pattern.CASE_INSENSITIVE);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {

        if (this.getText().equals("password")){
            this.setText("");
            InstanceLauncher.sethEditMode=true;

            App.launcher.disableLauncherInstance();
            final boolean openLauncher = true;
            SwingUtilities.invokeLater(() -> {
                new LauncherFrame(openLauncher);
            });        }
        if (e.getKeyChar() == KeyEvent.VK_ENTER) {
            Analytics.sendEvent(this.getText(), "Search", "Instance");
            this.parent.fireSearchEvent(new InstancesSearchEvent(e.getSource(), this.getSearchPattern()));
        }
    }
}
