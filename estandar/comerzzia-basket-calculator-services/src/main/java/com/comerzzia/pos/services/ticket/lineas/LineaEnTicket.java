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

package com.comerzzia.pos.services.ticket.lineas;

import java.util.LinkedList;
import java.util.List;


public class LineaEnTicket {

    protected List<String> lineas;
    protected String textoOriginal;

    public LineaEnTicket() {
        super();
        lineas = new LinkedList<String>();
    }

    /**
     *  Contruye nuestras líneas teniendo en cuenta que no se parten palabras
     * @param texto 
     */
    public LineaEnTicket(String texto) {
        this(texto, false);
    }

    /**
     *  Contruye nuestras líneas partiendo palabras si lo indica el booleano
     * @param texto
     * @param partirLineas
     */
    public LineaEnTicket(String texto, boolean justificar) {
        this();
        if (texto !=null && !texto.isEmpty()){
            String[] textos = texto.split("\\|");
            int bloque = 0;
            while (bloque<textos.length){
                costruyeLineas(textos[bloque], justificar);
                bloque++;
            }
        }
    }
    
    public void costruyeLineas(String textoCompleto, boolean justificar) {
        
        this.textoOriginal = textoCompleto;
        if (textoCompleto != null) {
            String[] textos = textoCompleto.split("\n");
            for (String texto : textos) {
                int ini = 0, fin;
                while (ini < texto.length()) {
                    fin = ini + 40;
                    if (fin < texto.length()) {
                        int retroceso = 0;
                        while (fin>=0 && texto.charAt(fin) != ' ') {
                            fin--;
                            retroceso++;
                        }
                        if(ini>=fin){
                            fin = ini + 40;
                        }
                        String linea = texto.substring(ini, fin).trim();
                        retroceso = 40 - linea.length();
                        if (justificar && retroceso > 0) {
                            int index = 0;
                            int index2 = 0;
                            String espacio = " ";
                            while (retroceso > 0) {
                                index2 = linea.indexOf(espacio, index);
                                if (index2 > 0) {
                                    linea = linea.substring(0, index2) + " " + linea.substring(index2);
                                    index = index2 + 2;
                                    retroceso--;
                                }
                                else if (index > 0) {
                                    index = 0;
                                    espacio += " ";
                                }
                                else {
                                    break;
                                }
                            }
                        }
                        lineas.add(linea);
                        ini = fin + 1;
                    }
                    else {
                        lineas.add(texto.substring(ini));
                        break;
                    }
                }
            }
        }
    }

    public List<String> getLineas() {
        return lineas;
    }

    public void setLineas(List<String> lineas) {
        this.lineas = lineas;
    }

    public String getTextoOriginal() {
        return textoOriginal;
    }
}