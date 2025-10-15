/**
 * ComerZZia 3.0
 *
 * Copyright (c) 2008-2015 Comerzzia, S.L.  All Rights Reserved.
 *
 * THIS WORK IS  SUBJECT  TO  SPAIN  AND  INTERNATIONAL  COPYRIGHT  LAWS  AND
 * TREATIES.   NO  PART  OF  THIS  WORK MAY BE  USED,  PRACTICED,  PERFORMED
 * COPIED, DISTRIBUTED, REVISED, MODIFIED, TRANSLATED,  ABRIDGED, CONDENSED,
 * EXPANDED,  COLLECTED,  COMPILED,  LINKED,  RECAST, TRANSFORMED OR ADAPTED
 * WITHOUT THE PRIOR WRITTEN CONSENT OF COMERZZIA, S.L. ANY USE OR EXPLOITATION
 * OF THIS WORK WITHOUT AUTHORIZATION COULD SUBJECT THE PERPETRATOR TO
 * CRIMINAL AND CIVIL LIABILITY.
 *
 * CONSULT THE END USER LICENSE AGREEMENT FOR INFORMATION ON ADDITIONAL
 * RESTRICTIONS.
 */


package com.comerzzia.pos.util.math;

import java.util.ArrayList;
import java.util.List;


public class Permutaciones {
    private final List<List<Integer>> permutaciones;
    private final Integer tamaño;
    
    public Permutaciones(Integer tamaño){
        this.tamaño = tamaño;
        permutaciones = new ArrayList<>();
    }
    
    public void permutar(){
        Integer [] elementos = new Integer[tamaño];
        for (int i = 0; i<tamaño; i++){
            elementos[i] = i;
        }
        permutar(elementos, new ArrayList<Integer>(), tamaño, tamaño);
    }

    public List<List<Integer>> getPermutaciones() {
        return permutaciones;
    }

    private void permutar(Integer[] elementos, List<Integer> permutacion, int n, int r) {
        if (n == 0) {
            permutaciones.add(permutacion);
            return;
        } 
        for (int i = 0; i < r; i++) {
            if (!permutacion.contains(elementos[i])) { 
                List<Integer> permutacionNueva = new ArrayList<>();
                permutacionNueva.addAll(permutacion);
                permutacionNueva.add(elementos[i]);
                permutar(elementos, permutacionNueva, n - 1, r);
            }
        }
    }
    
}
