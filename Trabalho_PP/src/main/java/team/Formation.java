package team;

import com.ppstudios.footballmanager.api.contracts.team.IFormation;

public class Formation implements IFormation {
    private String displayName;

    public Formation(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public int getTacticalAdvantage(IFormation formation) {
        if (formation == null) {
            throw new IllegalStateException("Formation didn't set");
        }
        if (this.displayName.equals("4-4-2") && formation.getDisplayName().equals("3-5-2")) {
            return 1;
        } else if (this.displayName.equals("3-5-2") && formation.getDisplayName().equals("4-4-2")) {
            return -1;
        } else {
            return 0;
        }
    }
}
