package a23.sim203.tp2.modele;

import javafx.scene.control.Alert;
import org.mariuszgromada.math.mxparser.*;

import javax.lang.model.element.VariableElement;
import java.util.*;

public class MoteurCalcul {

    // ajoutez les attributs pour stocker les équations et les variables
    private Expression expression;
    private Equation equation;

    public MoteurCalcul() {
        License.iConfirmNonCommercialUse("Cegep Limoilou");
        this.expression = new Expression();

    }


    private Set<String> determineToutesVariablesRequises() {
        return new HashSet<>(getToutesLesVariables());
    }

    private void ajouteVariable(String variable, double valeur) {
        Constant constant = new Constant(variable, valeur);
        expression.addConstants(constant);
    }

    public void setValeurVariable(String nomVariable, double valeur) {
        Constant var = expression.getConstant(nomVariable);
        var.setConstantValue(valeur);
    }

    public void ajouteEquation(String nouvelleEquation) {
        Equation equation1 = new Equation(nouvelleEquation,expression.getDescription());
        for (int i = 0; i < getToutesLesVariables().size() ; i++) {
            if (expression.getConstant(i) == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Équation non valide");
            }
        }
    }


    public void effaceEquation(String nomEquation) {


    }

    public double calcule(String nomEquation) {

        return 0; // à changer
    }

    public double calcule(Equation equation) {

        return 0; // à changer

    }

    public Collection<String> getToutesLesVariables() {


        return null; // à changer
    }

    public Equation getToutesLesEquations() {
        return null; // à changer

    }


    public Map<Object, Object> getVariableValueMap() {

        return null; // à changer

    }

    public Map<Object, Object> getEquationMap() {

        return null; // à changer

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
