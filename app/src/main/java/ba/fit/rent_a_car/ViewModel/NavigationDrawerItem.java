package ba.fit.rent_a_car.ViewModel;

public class NavigationDrawerItem {
    private String LabelNaziv;
    private int Icon;
    private String Counter;
    private boolean isCounterVisible;

    public NavigationDrawerItem(String labelNaziv, int icon, String counter, boolean isCounterVisible) {
        LabelNaziv = labelNaziv;
        Icon = icon;
        Counter = counter;
        this.isCounterVisible = isCounterVisible;
    }

    public NavigationDrawerItem(String labelNaziv, int icon) {
        LabelNaziv = labelNaziv;
        Icon = icon;
    }

    public String getLabelNaziv() {
        return LabelNaziv;
    }

    public void setLabelNaziv(String labelNaziv) {
        LabelNaziv = labelNaziv;
    }

    public int getIcon() {
        return Icon;
    }

    public void setIcon(int icon) {
        Icon = icon;
    }

    public String getCounter() {
        return Counter;
    }

    public void setCounter(String counter) {
        Counter = counter;
    }

    public boolean isCounterVisible() {
        return isCounterVisible;
    }

    public void setCounterVisible(boolean isCounterVisible) {
        this.isCounterVisible = isCounterVisible;
    }
}
