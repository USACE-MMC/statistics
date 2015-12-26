/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Distributions.LinearMoments;

/**
 *
 * @author Q0HECWPL
 */
public class Logistic extends Distributions.ContinuousDistribution{
    private double _K;
    private double _Alpha;
    private double _Xi;
    public Logistic(double[] data){
        MomentFunctions.LinearMoments LM = new MomentFunctions.LinearMoments(data);
        _K = -LM.GetT3();
        _Alpha = LM.GetL2() * java.lang.Math.sin(_K*java.lang.Math.PI)/(_K*java.lang.Math.PI);
        _Xi = LM.GetL1() - _Alpha * ((1/_K)-(java.lang.Math.PI/java.lang.Math.sin(_K * java.lang.Math.PI)));
        SetPeriodOfRecord(LM.GetSampleSize());
    }
    @Override
    public double GetInvCDF(double probability) {
        if(_K!=0){
            return _Xi + _Alpha * (1 - (java.lang.Math.pow((1 - probability) / probability, _K))) / _K;
        }else{
            return _Xi - _Alpha * (java.lang.Math.log((1 - probability) / probability));
        }
    }
    @Override
    public double GetCDF(double value) {
        return (1 / (1 + java.lang.Math.exp(-Y(value))));
    }
    @Override
    public double GetPDF(double value) {
        return (1/_Alpha) * java.lang.Math.exp(-(1 - _K) * Y(value)) / (java.lang.Math.pow((1 + java.lang.Math.exp(-Y(value))), 2));
    }
    private double Y(double value){
        if(_K!=0){
            return (1/-_K) * java.lang.Math.log(1 -_K * (value - _Xi) / _Alpha);
        }else{
            return (value - _Xi) / _Alpha;
        }
    }
}