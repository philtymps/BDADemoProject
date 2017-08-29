package com.extension.bda.userexits;

public class ShippingChargeBreakdown {

	private double _flatFee = 0;
	private double _weightFee = 0;
	private double _unitFee = 0;
	public ShippingChargeBreakdown(double flatFee, double weightFee, double unitFee){
		_flatFee = flatFee;
		_weightFee = weightFee;
		_unitFee = unitFee;
	}
	
	public void addWeightFee(double weightFee){
		_weightFee += weightFee;
	}
	public void addUnitFee(double unitFee){
		_unitFee += unitFee;
	}
	public double getFee(){
		if(_flatFee > _weightFee && _flatFee > _unitFee){
			return _flatFee;
		} else if(_weightFee > _unitFee){
			return _weightFee;
		} else {
			return _unitFee;
		}
	}
}
