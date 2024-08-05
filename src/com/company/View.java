package com.company;
/**
 *Objectif: Analyser le texte d'un JTextArea d'en extraire les mots et les nombres, d'effectuer des recherches et des tris
 * @author Redgy Pérard
 * 2020
 */

import javax.swing.*;
import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class View {
    JFrame frame; //frame pour voir
    JTextField txfRecherche; //texte à rechercher
    JButton btnRechercher; //bouton rechercher
    JButton btnExtraireMots; //bouton extraire mots texte
    JButton btnViderMots; //bouton vider mots textarea
    JButton btnExtraireNbrs; //bouton extraire nombres textearea
    JButton btnViderNbrs; //bouton vider nombres textarea
    JButton btnTrierMots; //bouton trier mots textarea
    JButton btnTrierNbrs; //bouton trier nombres textarea
    JButton btnInfoMots; //bouton info des mots
    JButton btnInfoNbrs; //bouton info des nombres
    JButton btnQuitter; //bouton pour quitter
    JLabel labFouille; //label pour mot trouvé
    JLabel labMots; //label pour nombre mots textarea
    JLabel labNbrs; //label pour nombre textarea
    JRadioButton radCroissant; //radiobouton croissant
    JRadioButton radDecroissant; //radiobouton decroissant
    ButtonGroup grp12; //bouton grouper les radiobouton
    JCheckBox chbDoublons; //checkbox pour double mots ou nombres
    JTextArea txaTexte; //textarea pour texte
    JTextArea txaMots; //textarea pour mots
    JTextArea txaNombres; //textarea pour nombres
    JPanel panCenter; //panel de centre
    JPanel panRight; //panel de droite
    JPanel panNorth; //panel du haut
    JPanel panSouth; //panel du sud

    //création de dimension pour composants
    Dimension dimtxf = new Dimension(200, 30);
    Dimension dimtxa = new Dimension(185, 550);
    Dimension dimbtn = new Dimension(180, 30);
    Dimension dimlab = new Dimension(75, 30);

    public View() {
        //settings du frame
        frame = new JFrame("TP3 Redgy Pérard 1473597");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(900, 800);
        frame.setMinimumSize(new Dimension(850, 700));

        //création du jtextfield
        txfRecherche = new JTextField();
        txfRecherche.setPreferredSize(dimtxf);

        //création du bouton rechercher
        btnRechercher = new JButton("Rechercher");
        btnRechercher.addActionListener(e -> btnRechercherAction());
        btnRechercher.setPreferredSize(dimbtn);

        //création de textareas
        txaTexte = new JTextArea();
        txaTexte.setLineWrap(true);
        txaTexte.setWrapStyleWord(true);
        txaTexte.setPreferredSize(dimtxa);

        txaMots = new JTextArea();
        txaMots.setLineWrap(true);
        txaMots.setWrapStyleWord(true);
        txaMots.setPreferredSize(dimtxa);

        txaNombres = new JTextArea();
        txaNombres.setLineWrap(true);
        txaNombres.setWrapStyleWord(true);
        txaNombres.setPreferredSize(dimtxa);

        //création de labels pour statistiques
        labFouille = new JLabel();

        labMots = new JLabel("Mots : 0     ");
        labMots.setMaximumSize(dimlab);
        labMots.setAlignmentY(Component.CENTER_ALIGNMENT);

        labNbrs = new JLabel("Nombres : 0 ");
        labNbrs.setMaximumSize(dimlab);
        labNbrs.setAlignmentY(Component.CENTER_ALIGNMENT);

        //création des radiobouton croissant et décroissant
        radCroissant = new JRadioButton("tri croissant", true);
        radDecroissant = new JRadioButton("tri décroissant");

        //création du groupebouton pour les radiobouton
        grp12 = new ButtonGroup();

        createNorth();
        createCenter();
        createSouth();
        btnFaireCeciAction();
        btnFaireCelaAction();
        createEast();

        txaNombres.setEditable(false);
        txaMots.setEditable(false);

        frame.setVisible(true);

    }

    public void createNorth() {
        panNorth = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panNorth.add(txfRecherche);
        panNorth.add(btnRechercher);
        panNorth.add(labFouille);
        frame.add(panNorth, BorderLayout.NORTH);
    }

    public void createCenter() {
        panCenter = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panCenter.add(new JScrollPane(txaTexte));
        panCenter.add(new JScrollPane(txaMots));
        panCenter.add(new JScrollPane(txaNombres));
        frame.add(panCenter, BorderLayout.CENTER);
    }

    public void createEast() {
        panRight = new JPanel();
        panRight.setLayout(new BoxLayout(panRight, BoxLayout.Y_AXIS));
        panRight.add(chbDoublons);
        panRight.add(btnExtraireMots);
        btnExtraireMots.setMaximumSize(dimbtn);
        panRight.add(btnViderMots);
        btnViderMots.setMaximumSize(dimbtn);
        panRight.add(Box.createRigidArea(new Dimension(0, 50)));
        panRight.add(btnExtraireNbrs);
        btnExtraireNbrs.setMaximumSize(dimbtn);
        panRight.add(btnViderNbrs);
        panRight.add(Box.createRigidArea(new Dimension(0, 50)));
        panRight.add(radCroissant);
        panRight.add(radDecroissant);
        grp12.add(radCroissant);
        grp12.add(radDecroissant);
        panRight.add(btnTrierMots);
        panRight.add(btnTrierNbrs);
        panRight.add(Box.createRigidArea(new Dimension(0, 50)));
        panRight.add(btnInfoMots);
        panRight.add(btnInfoNbrs);
        panRight.add(btnQuitter);
        frame.add(panRight, BorderLayout.EAST);
    }

    public void createSouth() {
        panSouth = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panSouth.add(labMots);
        panSouth.add(labNbrs);
        frame.add(panSouth, BorderLayout.SOUTH);
    }

    private void btnFaireCeciAction() {
        chbDoublons = new JCheckBox("Permettre doublons");
        chbDoublons.setSelected(true);
        chbDoublons.addActionListener(e -> chbDoublonsAction());

        btnExtraireMots = new JButton("Extraire Mots");
        btnExtraireMots.setPreferredSize(dimbtn);
        btnExtraireMots.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnExtraireMots.addActionListener(e -> btnExtraireMotsAction());

        btnViderMots = new JButton("Vider Mots");
        btnViderMots.setPreferredSize(dimbtn);
        btnViderMots.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnViderMots.addActionListener(e -> btnViderMotsAction());


        btnExtraireNbrs = new JButton("Extraire Nombres");
        btnExtraireNbrs.setPreferredSize(dimbtn);
        btnExtraireNbrs.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnExtraireNbrs.addActionListener(e -> btnExtraireNbrsAction());


        btnViderNbrs = new JButton("Vider Nombres");
        btnViderNbrs.setMaximumSize(dimbtn);
        btnViderNbrs.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnViderNbrs.addActionListener(e -> btnViderNbrsAction());

    }

    private void btnFaireCelaAction() {
        btnTrierMots = new JButton("Trier Mots");
        btnTrierMots.setMaximumSize(dimbtn);
        btnTrierMots.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnTrierMots.addActionListener(e -> btnTrierMotsAction());

        btnTrierNbrs = new JButton("Trier Nombres");
        btnTrierNbrs.setMaximumSize(dimbtn);
        btnTrierNbrs.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnTrierNbrs.addActionListener(e -> btnTrierNbrsAction());

        btnInfoMots = new JButton("Info Mots");
        btnInfoMots.setMaximumSize(dimbtn);
        btnInfoMots.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnInfoMots.addActionListener(e -> btnInfoMotsAction());

        btnInfoNbrs = new JButton("Info Nombres");
        btnInfoNbrs.setMaximumSize(dimbtn);
        btnInfoNbrs.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnInfoNbrs.addActionListener(e -> btnInfoNbrsAction());

        btnQuitter = new JButton("Quitter");
        btnQuitter.setMaximumSize(dimbtn);
        btnQuitter.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnQuitter.addActionListener(e -> btnQuitterAction());
    }


    private void chbDoublonsAction() {

    }

    private void btnInfoNbrsAction() {

        InfoNombres();
    }

    private void btnInfoMotsAction() {

        InfoMots();
    }

    private void btnTrierNbrsAction() {
        if (radCroissant.isSelected()) {
            String[] tableau = txaNombres.getText().split("\n"); //tableau qui stocke le text du textarea nombres
            int t = tableau.length; //transfert la longeur du tableau dans un integer
            int[] newTableau = new int[t]; //nouveau tableau pour recevoir les données du tableau précédant
            for (int i = 0; i < t; i++) {
                newTableau[i] = Integer.parseInt(tableau[i]);
            }
            triSSSintup(newTableau);
            txaNombres.setText("");
            for (int i = 0; i < newTableau.length; i++) {
                txaNombres.append(newTableau[i] + "\n");
            }
        } else if (radDecroissant.isSelected()) {
            String[] tab = txaNombres.getText().split("\n"); //tableau qui stocke le text du textarea nombres
            int z = tab.length; //transfert la longeur du tableau dans un integer
            int[] newTab = new int[z]; //nouveau tableau pour recevoir les données du tableau précédant
            for (int i = 0; i < z; i++) {
                newTab[i] = Integer.parseInt(tab[i]);
            }
            triSSSintdown(newTab);
            txaNombres.setText("");
            for (int i = 0; i < newTab.length; i++) {
                txaNombres.append(newTab[i] + "\n");
            }
        }


    }

    private void btnTrierMotsAction() {
        if (radDecroissant.isSelected()) {
            String[] arreys = txaMots.getText().split("\n"); //tableau qui stocke le text du textarea mots
            int taille = arreys.length; //transfert la longeur du tableau dans un integer
            String[] newArreys = new String[taille]; //nouveau tableau pour recevoir les données du tableau précédant
            for (int i = 0; i < taille; i++) {
                newArreys[i] = arreys[i];
            }
            triSSSDownString(newArreys);
            txaMots.setText("");
            for (int i = 0; i < newArreys.length; i++) {
                txaMots.append(newArreys[i] + "\n");
            }
        } else if (radCroissant.isSelected()) {
            String[] arrai = txaMots.getText().split("\n"); //tableau qui stocke le text du textarea mots
            int taille = arrai.length; //transfert la longeur du tableau dans un integer
            String[] newArrai = new String[taille]; //nouveau tableau pour recevoir les données du tableau précédant
            for (int i = 0; i < taille; i++) {
                newArrai[i] = arrai[i];
            }
            triSSSUpString(newArrai);
            txaMots.setText("");
            for (int i = 0; i < newArrai.length; i++) {
                txaMots.append(newArrai[i] + "\n");
            }
        }

    }

    private void btnViderNbrsAction() {
        txaNombres.setText("");
        labNbrs.setText("Nombres : 0");
    }

    private void btnExtraireNbrsAction() {
        String[] tab = txaTexte.getText().split("\\s"); //tableau qui stocke le text du textarea texte
        txaNombres.setText("");
        for (String elem : tab) {
            try {
                if (Integer.parseInt(elem) < Integer.MAX_VALUE)
                    elem = elem.replaceFirst("0*", "");
                elem = elem.replaceFirst("-0*", "-");
                txaNombres.append(elem + "\n");


            } catch (NumberFormatException e) {
            }
        }
        String[] tabNbrs = txaNombres.getText().split("\\s"); //tableau qui stocke le text du textarea texte
        labNbrs.setText("Nombres : " + (tabNbrs.length));
    }

    private void btnViderMotsAction() {
        txaMots.setText("");
        labMots.setText("Mots : 0  ");
    }

    private void btnExtraireMotsAction() {
        Pattern regex = Pattern.compile("(aujourd'hui)|\\b[A-Za-zé]+[']|\\b([A-Za-zé]+)");
        Matcher match = regex.matcher(txaTexte.getText());
        while (match.find()) {
            txaMots.append(match.group() + "\n");
        }
        if (!chbDoublons.isSelected()) {
            String[] tabMots = txaMots.getText().split("\\n"); //tableau qui stocke le text du textarea mots
            for (int i = 0; i < tabMots.length; i++)
                for (int j = i + 1; j < tabMots.length; j++)
                    if (tabMots[j].compareTo(tabMots[i]) == 0) {
                        tabMots[i] = "";
                    }
            txaMots.setText("");
            for (int r = 0; r < tabMots.length; r++) {
                if (tabMots[r].compareTo("") != 0) {
                    txaMots.append(tabMots[r] + "\n");
                }
            }

        }
        String[] tabMots = txaMots.getText().split("\\s"); //tableau qui stocke le text du textarea mots
        labMots.setText("Mots : " + (tabMots.length));
    }

    private void btnRechercherAction() {
        int compteur = 0;
        int indice2 = 0;
        int indice = 0;
        int[] tabindex = new int[1];
        String[] tab = txaMots.getText().split("\n");
        if (!txfRecherche.getText().isEmpty()) {
            indice = nextIndexOf(tab, txfRecherche.getText(), indice2);
            tabindex[compteur] = indice;
            labFouille.setText("mot trouvé à l'index : " + tabindex[0]);
            while(indice != -1){
                compteur++;
                indice2 = indice+1;
                indice = nextIndexOf(tab,txfRecherche.getText(),indice2);
                tabindex = ajouterElementTab(tabindex);
                tabindex[compteur]=indice;
                labFouille.setText(compteur + " mot trouvé à l'index : ");
                if (indice2 == tab.length){
                    indice = -1;
                }
                for (int i = 0; i <= compteur-1;i++){
                    if(compteur == 1)
                        labFouille.setText( compteur + " mot trouvé à l'index : " + tabindex[0]);
                    else if (compteur > 1){
                        labFouille.setText(labFouille.getText() + " " + tabindex[i]);
                    }
                }
            }
            if (compteur == 0){
                labFouille.setText( compteur + " mot trouvé");
            }
        }

    }

    /**
     * Trouve l'indice du mot recherché
     * @param tab de chaîne
     * @param texte de chaîne à parcourir
     * @param index indice départ recherche
     * @return indice mot trouvé
     */
    public int nextIndexOf(String[]tab, String texte,int index){
      int retourIndex = 0;
      for(int i= index; i < tab.length; i++){
          if (tab[i].compareTo(texte) != 0){
              retourIndex =-1;
          }
          else{
              retourIndex = i;
              i = tab.length;
          }
      }
      return retourIndex;
    }
    /**Ajoute une cellule au tableau
     *@param ancienTableauIndex tableau d'integer
     * return tableau d'integer
     */
    public static int[] ajouterElementTab(int[] ancienTableauIndex) {
        int[] nouveauTab = new int[ancienTableauIndex.length + 1];
        for (int i = 0; i < ancienTableauIndex.length; i++) {
            nouveauTab[i] = ancienTableauIndex[i];
        }
        return nouveauTab;
    }
    /**
     * Trie en ordre croissant les valeurs du tableau tab
     * @param tab tableau de intenger
     */
    public void triSSSintup(int[]tab){
        for(int i =0; i < tab.length -1; i++){
            int imin = tab.length; //transfert la longeur du tableau dans un integer
            int min = i; //variable pour permutation
            for (int j = i+1; j < tab.length; j++){
                if(tab[j] < tab[i]){
                    imin = tab[j];
                    min = j;
                    int r = tab[i];
                    tab[i] = imin;
                    tab[min] = r;
                }
            }

        }
    /**
     * Trie en ordre décroissant les valeurs du tableau tab
     * @param tableau tableau de integer
     */
    }
    public void triSSSintdown(int[]tab){
        for(int i =0; i < tab.length -1; i++){
            int imax = tab.length; //transfert la longeur du tableau dans un integer
            int max = i; //variable pour permutation
            for (int j = i+1; j < tab.length; j++){
                if(tab[j] > tab[i]){
                    imax = tab[j];
                    max = j;
                    int r = tab[i];
                    tab[i] = imax;
                    tab[max] = r;
                }
            }

        }

    }
    /**
     *Trie en ordre décroissant le premier caractère de la chaîne de caractères
     * @param table table de chaînes
     */
    public void triSSSDownString(String[]table){
        for (int i = 0; i < table.length - 1;i++){
            String sLonger;//stockage de la chaîne
            for (int j = i+1;j < table.length; j++){
                if (table[j].compareTo(table[i])>0){
                    sLonger = table[i];
                    table[i] = table[j];
                    table[j] = sLonger;
                }
            }
        }
    }
    /**
     *Trie en ordre croissant le premier caractère de la chaîne de caractères
     * @param tablle tablle de chaînes
     */
    public void triSSSUpString(String[]tablle){
        for (int i = 0; i < tablle.length - 1;i++){
            String sShorter;//stockage de la chaîne
            for (int j = i+1;j < tablle.length; j++){
                if (tablle[j].compareTo(tablle[i])<0){
                    sShorter = tablle[i];
                    tablle[i] = tablle[j];
                    tablle[j] = sShorter;
                }
            }
        }
    }
    /**Parcourt le tableau pour trouver le nombre le plus petit
     *@param tab tab de integer à parcourir
     *@return le mot le plus court
     */
    public static int InfoNombresCourt(int[]tab){
        int min = 0; //stock valeur du nombre le plus court
        for (int i = 0; i < tab.length;i++){
            if (min>tab[i])
                min = tab[i];
        }
        return min;
    }
    /**Parcourt le tableau pour trouver le nombre le plus grand
     *@param tableau tab d'integer à parcourir
     *@return le mot le plus grand
     */
    public static int InfoNombresLong(int[]tableau){
        int max = 0; //stock la valeur du mot le plus long
        for (int i = 0; i< tableau.length;i++){
            if (max < tableau[i])
                max = tableau[i];
        }
        return max;
    }
    /**
     *Apelle les méthodes pour stocker les valeurs du nombre plus petit et plus grand
     */
    public void InfoNombres(){
        int plusCourt; //stocke le nombre le plus court
        int pluslong; //stocke le nombre le plus long
        String[]tab = txaNombres.getText().split("\\s"); //tableau qui stocke le text du textarea nombres
        int taille = tab.length; //transfert la longeur du tableau dans un integer
        int[]nombre = new int[taille]; //nouveau tableau pour recevoir les données du tableau précédant
        for (int i = 0; i < taille;i++){
            nombre[i] = Integer.parseInt((tab[i]));
        }
        plusCourt = InfoNombresCourt(nombre); //stocke la valeur du plus petit nombre du tableau de la méthode précédante
        pluslong = InfoNombresLong(nombre); //stocke la valeur du plus grand nombre du tableau de la méthode précédante
        JOptionPane.showMessageDialog(frame,"Le nombre le plus petit est : " + plusCourt + " et le nombre le plus grand est : " + pluslong);
    }
    /**Parcourt le tableau pour trouver le mot le plus court
     *@param tableCourt tablecourt de chaînes
     *@return mot le plus court
     */
    public String InfoMotsCourt(String[]tableCourt){
        String court = tableCourt[0];
        for (int i = 0; i < tableCourt.length; i ++){
            if(court.length()>tableCourt[i].length()){
                court = tableCourt[i];
            }
        }
        return court;

    }
    /**Parcourt le tableau pour trouver le mot le plus long
     *@param tableLong tablelong de chaînes à parcourir
     *@return le mots le plus long
     */
    public String InfoMotsLong(String[]tableLong){
        String longue = tableLong[0];
        for (int i = 0; i < tableLong.length; i ++){
            if(longue.length()<tableLong[i].length()){
                longue = tableLong[i];
            }
        }
        return longue;

    }
    /**
     *Apelle les méthodes pour stocker les valeurs du mot le plus petit et plus grand
     */
    public void InfoMots(){
        String court; //stockera le mot le plus court
        String longue; //stockera le mot le plus long
        String[]table = txaMots.getText().split("\\s"); //tableau qui stocke le text du textarea mots
        court = InfoMotsCourt(table); // stocke la valeur du plus petit mot du tableau de la méthode précédante
        longue = InfoMotsLong(table); //stocke la valeur du plus grand mot du tableau de la méthode précédante
        JOptionPane.showMessageDialog(frame,"Le mot le plus court est : " + court + "   et le nombre le plus long est : " + longue);
    }
    private void btnQuitterAction() {
        int rep = JOptionPane.showConfirmDialog(frame, "Voulez-vous vraiment quitter?","Confirmation",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        if (rep == 0)
            System.exit(0);
    }
    public static void main(String[] args) {
        View maVue = new View();
    }
}
