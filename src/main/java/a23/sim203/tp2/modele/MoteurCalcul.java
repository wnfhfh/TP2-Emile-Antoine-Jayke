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

    public void ajouteEquation(String newEquation) {
        try {
            Equation equation = parseEquation(newEquation);
            equationMap.put(equation.getNom(), equation);
            equationEtVariableMap.put(equation.getNom(), equation);
            addVariablesFromEquation(equation);
        } catch (RuntimeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Équation non valide");
        }
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
                ajouteVariable(nomVariable, 0.0);
                equationEtVariableMap.put(nomVariable, 0.0);
            }
        }
    }

    public void effaceEquation(String nomEquation) {
        if (equationMap.containsKey(nomEquation)) {
            Equation equation = equationMap.get(nomEquation);
            Expression associatedExpression = new Expression(equation.getExpression());
            equationMap.remove(nomEquation);
            if (associatedExpression != null) {
                variableMap.replace(associatedExpression.getExpressionString(), new Constant(associatedExpression.getExpressionString(), 0.0));
            }
            variableMap.keySet().removeIf(variable -> !equationMap.containsKey(variable));
        }
    }

    public double calcule(String nomEquation) {
        Equation equation = equationMap.get(nomEquation);
        Double resultat = 0.0;
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
        System.out.println(expressionStringTemp);
        resultat = new Expression(expressionStringTemp).calculate();
//        if (expression != null) {
//            resultat = expression.calculate();
//        }
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
//        equation.getElementsRequis();
//        equation.getExpression();
//
//        Expression expression = new Expression(equation.getExpression(), (PrimitiveElement) equation.getElementsRequis());
//        expression.calculate();
//        essayé de commencer la méthode sans succès

        return 0; // à changer

    }

    public Collection<String> getToutesLesVariables() {
        HashSet<String> toutesLesVariables = new HashSet<String>();

        Iterator iterator = variableMap.values().iterator();
        while (iterator.hasNext()) {
            Expression expressionTemp = (Expression) iterator.next();
        }

        return toutesLesVariables; // à changer
    }

    public Equation getToutesLesEquations() {
        return null; // à changer

    }


    public Map<String, Constant> getVariableValueMap() {
        return variableMap; // à changer
    }

    public Map<String, Equation> getEquationMap() {
        return equationMap;
    }

    public static void main(String[] args) {

        // Comment utiliser les expression avec plusieurs variables

        License.iConfirmNonCommercialUse("Cegep Limoilou");

        Constant A0 = new Constant("a0", 3);
        Constant B0 = new Constant("b0", 3);

        Expression e1 = new Expression("3+4+a0+b0", A0, B0);
        Expression e2 = new Expression("3+4+a0+b0", new Constant[]{A0, B0});// alternative avec tableau

        System.out.println("e1=" + e1.calculate());
        System.out.println("e2=" + e2.calculate());

    }
}
