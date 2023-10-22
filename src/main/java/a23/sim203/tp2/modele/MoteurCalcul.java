package a23.sim203.tp2.modele;

import javafx.scene.control.Alert;
import org.mariuszgromada.math.mxparser.Constant;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.License;

import java.util.*;

public class MoteurCalcul {

    // ajoutez les attributs pour stocker les équations et les variables
    private HashMap<String, Constant> variableMap;
    private HashMap<String, Equation> equationMap;
    private HashMap<String, Object> equationEtVariableMap;

    public MoteurCalcul() {
        License.iConfirmNonCommercialUse("Cegep Limoilou");
        variableMap = new HashMap<>();
        equationMap = new HashMap<>();
        equationEtVariableMap = new HashMap<>();

    }

    private Set<String> determineToutesVariablesRequises() {
        return (Set<String>) getToutesLesVariables();
    }

    private void ajouteVariable(String variable, double valeur) {
        variableMap.put(variable, new Constant(variable, valeur));
    }

    public void setValeurVariable(String nomVariable, double valeur) {
        variableMap.put(nomVariable, new Constant(nomVariable, valeur));
    }

    public void ajouteEquation(String nouvelleEquation) {
        try {
            Equation equation = parseEquation(nouvelleEquation);
            equationMap.put(equation.getNom(), equation);
            equationEtVariableMap.put(equation.getNom(), equation);
            addVariablesFromEquation(equation);
            if (variableMap.containsKey(equation.getNom())) {
                variableMap.remove(equation.getNom());
            }
            retireVariablesInutiles();
        } catch (RuntimeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Équation non valide");
        }
    }

    private void retireVariablesInutiles() {
        Set<String> variablesInutiles = getAllVariables();
        variablesInutiles.removeAll(getAllElementsRequis());

        Iterator<String> iterator = variablesInutiles.iterator();
        if (variablesInutiles.size() > 0) {
            while (iterator.hasNext()) {
                String variableTemp = iterator.next();
                variableMap.remove(variableTemp);
                equationEtVariableMap.remove(variableTemp);
            }
        }
    }

    private Set<String> getAllElementsRequis() {
        Set<String> variablesRequises = new HashSet<>();
        Iterator<Equation> iterator = equationMap.values().iterator();
        while (iterator.hasNext()) {
            variablesRequises.addAll(iterator.next().getElementsRequis());
        }
        return variablesRequises;
    }

    private Equation parseEquation(String equationString) {
        String[] equationSplit = equationString.split("=");

        return new Equation(equationSplit[0], equationSplit[1]);
    }

    private void addVariablesFromEquation(Equation equation) {
        Set<String> elementsRequis = equation.getElementsRequis();
        Iterator<String> iterator = elementsRequis.iterator();
        while (iterator.hasNext()) {
            String nomVariable = iterator.next();
            if (!equationEtVariableMap.containsKey(nomVariable)) {
                ajouteVariable(nomVariable, Double.NaN);
                equationEtVariableMap.put(nomVariable, Double.NaN);
            }
        }
    }

    public void effaceEquation(String nomEquation) {
        if (equationMap.containsKey(nomEquation)) {
            Equation equation = equationMap.get(nomEquation);
            Expression associatedExpression = new Expression(equation.getExpression());
            equationMap.remove(nomEquation);
            variableMap.replace(associatedExpression.getExpressionString(), new Constant(associatedExpression.getExpressionString(), Double.NaN));
            variableMap.keySet().removeIf(variable -> !equationMap.containsKey(variable));
        }
    }

    public double calcule(String nomEquation) {
        Equation equation = equationMap.get(nomEquation);
        Double resultat = Double.NaN;
        Set<String> elementsRequis = equation.getElementsRequis();
        ArrayList<Constant> constants = new ArrayList();

        for (String element : elementsRequis) {
            if (variableMap.containsKey(element))
                constants.add(variableMap.get(element));
        }
        String expressionStringTemp = equation.getExpression();
        for (int i = 0; i < constants.size(); i++) {
            expressionStringTemp = expressionStringTemp.replace(constants.get(i).getConstantName(), Double.toString(constants.get(i).getConstantValue()));
        }


        resultat = new Expression(expressionStringTemp).calculate();


        return resultat;
    }

    public Set<String> getAllVariables() {
        return variableMap.keySet();
    }

    public Collection<Equation> getAllEquations() {
        return equationMap.values();
    }

    public HashMap<String, Constant> getVariableValues() {
        return variableMap;
    }

    public HashMap<String, Equation> getEquationExpressions() {
        return equationMap;
    }


    public double calcule(Equation equation) {
        Double resultat = Double.NaN;
        Set<String> elementsRequis = equation.getElementsRequis();
        ArrayList<Constant> constants = new ArrayList();

        for (String element : elementsRequis) {
            if (variableMap.containsKey(element))
                constants.add(variableMap.get(element));
        }

        String expressionStringTemp = equation.getExpression();
        for (int i = 0; i < constants.size(); i++) {
            expressionStringTemp = expressionStringTemp.replace(constants.get(i).getConstantName(), Double.toString(constants.get(i).getConstantValue()));
        }
        resultat = new Expression(expressionStringTemp).calculate();

        return resultat;
    }

    public Collection<String> getToutesLesVariables() {
        HashSet<String> toutesLesVariables = new HashSet<String>();

        Iterator<Constant> iteratorValues = variableMap.values().iterator();
        while (iteratorValues.hasNext()) {
            Constant constantTemp = iteratorValues.next();
            toutesLesVariables.add(constantTemp.getConstantName() + " = " + constantTemp.getConstantValue());
        }

        return toutesLesVariables;
    }

    public Collection<Equation> getToutesLesEquations() {
        return equationMap.values();
    }


    public Map<String, Constant> getVariableValueMap() {
        return variableMap; // à changer
    }

    public Map<String, Equation> getEquationMap() {
        return equationMap;
    }

    public static void main(String[] args) {

        // Comment utiliser les expressions avec plusieurs variables

        License.iConfirmNonCommercialUse("Cegep Limoilou");

        Constant A0 = new Constant("a0", 3);
        Constant B0 = new Constant("b0", 3);

        Expression e1 = new Expression("3+4+a0+b0", A0, B0);
        Expression e2 = new Expression("3+4+a0+b0", new Constant[]{A0, B0});// alternative avec tableau

        System.out.println("e1=" + e1.calculate());
        System.out.println("e2=" + e2.calculate());

    }
}
