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
package com.comerzzia.pos.persistence.clientes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClienteExample {
    public static final String ORDER_BY_UID_ACTIVIDAD = "UID_ACTIVIDAD";

    public static final String ORDER_BY_UID_ACTIVIDAD_DESC = "UID_ACTIVIDAD DESC";

    public static final String ORDER_BY_CODCLI = "CODCLI";

    public static final String ORDER_BY_CODCLI_DESC = "CODCLI DESC";

    public static final String ORDER_BY_DESCLI = "DESCLI";

    public static final String ORDER_BY_DESCLI_DESC = "DESCLI DESC";

    public static final String ORDER_BY_NOMBRE_COMERCIAL = "NOMBRE_COMERCIAL";

    public static final String ORDER_BY_NOMBRE_COMERCIAL_DESC = "NOMBRE_COMERCIAL DESC";

    public static final String ORDER_BY_DOMICILIO = "DOMICILIO";

    public static final String ORDER_BY_DOMICILIO_DESC = "DOMICILIO DESC";

    public static final String ORDER_BY_POBLACION = "POBLACION";

    public static final String ORDER_BY_POBLACION_DESC = "POBLACION DESC";

    public static final String ORDER_BY_PROVINCIA = "PROVINCIA";

    public static final String ORDER_BY_PROVINCIA_DESC = "PROVINCIA DESC";

    public static final String ORDER_BY_CP = "CP";

    public static final String ORDER_BY_CP_DESC = "CP DESC";

    public static final String ORDER_BY_TELEFONO1 = "TELEFONO1";

    public static final String ORDER_BY_TELEFONO1_DESC = "TELEFONO1 DESC";

    public static final String ORDER_BY_TELEFONO2 = "TELEFONO2";

    public static final String ORDER_BY_TELEFONO2_DESC = "TELEFONO2 DESC";

    public static final String ORDER_BY_FAX = "FAX";

    public static final String ORDER_BY_FAX_DESC = "FAX DESC";

    public static final String ORDER_BY_CODPAIS = "CODPAIS";

    public static final String ORDER_BY_CODPAIS_DESC = "CODPAIS DESC";

    public static final String ORDER_BY_PERSONA_CONTACTO = "PERSONA_CONTACTO";

    public static final String ORDER_BY_PERSONA_CONTACTO_DESC = "PERSONA_CONTACTO DESC";

    public static final String ORDER_BY_EMAIL = "EMAIL";

    public static final String ORDER_BY_EMAIL_DESC = "EMAIL DESC";

    public static final String ORDER_BY_CIF = "CIF";

    public static final String ORDER_BY_CIF_DESC = "CIF DESC";

    public static final String ORDER_BY_ID_TRAT_IMPUESTOS = "ID_TRAT_IMPUESTOS";

    public static final String ORDER_BY_ID_TRAT_IMPUESTOS_DESC = "ID_TRAT_IMPUESTOS DESC";

    public static final String ORDER_BY_ID_MEDPAG_VEN = "ID_MEDPAG_VEN";

    public static final String ORDER_BY_ID_MEDPAG_VEN_DESC = "ID_MEDPAG_VEN DESC";

    public static final String ORDER_BY_CODTAR = "CODTAR";

    public static final String ORDER_BY_CODTAR_DESC = "CODTAR DESC";

    public static final String ORDER_BY_CODSEC = "CODSEC";

    public static final String ORDER_BY_CODSEC_DESC = "CODSEC DESC";

    public static final String ORDER_BY_BANCO = "BANCO";

    public static final String ORDER_BY_BANCO_DESC = "BANCO DESC";

    public static final String ORDER_BY_BANCO_DOMICILIO = "BANCO_DOMICILIO";

    public static final String ORDER_BY_BANCO_DOMICILIO_DESC = "BANCO_DOMICILIO DESC";

    public static final String ORDER_BY_BANCO_POBLACION = "BANCO_POBLACION";

    public static final String ORDER_BY_BANCO_POBLACION_DESC = "BANCO_POBLACION DESC";

    public static final String ORDER_BY_CCC = "CCC";

    public static final String ORDER_BY_CCC_DESC = "CCC DESC";

    public static final String ORDER_BY_OBSERVACIONES = "OBSERVACIONES";

    public static final String ORDER_BY_OBSERVACIONES_DESC = "OBSERVACIONES DESC";

    public static final String ORDER_BY_ACTIVO = "ACTIVO";

    public static final String ORDER_BY_ACTIVO_DESC = "ACTIVO DESC";

    public static final String ORDER_BY_FECHA_ALTA = "FECHA_ALTA";

    public static final String ORDER_BY_FECHA_ALTA_DESC = "FECHA_ALTA DESC";

    public static final String ORDER_BY_FECHA_BAJA = "FECHA_BAJA";

    public static final String ORDER_BY_FECHA_BAJA_DESC = "FECHA_BAJA DESC";

    public static final String ORDER_BY_RIESGO_CONCEDIDO = "RIESGO_CONCEDIDO";

    public static final String ORDER_BY_RIESGO_CONCEDIDO_DESC = "RIESGO_CONCEDIDO DESC";

    public static final String ORDER_BY_CODCLI_FACTURA = "CODCLI_FACTURA";

    public static final String ORDER_BY_CODCLI_FACTURA_DESC = "CODCLI_FACTURA DESC";

    public static final String ORDER_BY_LOCALIDAD = "LOCALIDAD";

    public static final String ORDER_BY_LOCALIDAD_DESC = "LOCALIDAD DESC";

    public static final String ORDER_BY_CODTIPOIDEN = "CODTIPOIDEN";

    public static final String ORDER_BY_CODTIPOIDEN_DESC = "CODTIPOIDEN DESC";
    
    public static final String ORDER_BY_DEPOSITO = "DEPOSITO";

    public static final String ORDER_BY_DEPOSITO_DESC = "DEPOSITO DESC";
    
    public static final String ORDER_BY_CODLENGUA = "CODLENGUA";

    public static final String ORDER_BY_CODLENGUA_DESC = "CODLENGUA DESC";

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ClienteExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> activoCriteria;

        protected List<Criterion> allCriteria;

        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
            activoCriteria = new ArrayList<Criterion>();
        }

        public List<Criterion> getActivoCriteria() {
            return activoCriteria;
        }

        protected void addActivoCriterion(String condition, Object value, String property) {
            if (value != null) {
                activoCriteria.add(new Criterion(condition, value, "com.comerzzia.core.util.mybatis.typehandlers.BooleanStringTypeHandler"));
                allCriteria = null;
            }
        }

        protected void addActivoCriterion(String condition, Boolean value1, Boolean value2, String property) {
            if (value1 != null && value2 != null) {
                activoCriteria.add(new Criterion(condition, value1, value2, "com.comerzzia.core.util.mybatis.typehandlers.BooleanStringTypeHandler"));
                allCriteria = null;
            }
        }

        public boolean isValid() {
            return criteria.size() > 0
                || activoCriteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            if (allCriteria == null) {
                allCriteria = new ArrayList<Criterion>();
                allCriteria.addAll(criteria);
                allCriteria.addAll(activoCriteria);
            }
            return allCriteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition != null) {
                criteria.add(new Criterion(condition));
                allCriteria = null;
            }
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value != null) {
                criteria.add(new Criterion(condition, value));
                allCriteria = null;
            }
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 != null && value2 != null) {
                criteria.add(new Criterion(condition, value1, value2));
                allCriteria = null;
            }
        }

        public Criteria andUidActividadIsNull() {
            addCriterion("UID_ACTIVIDAD is null");
            return (Criteria) this;
        }

        public Criteria andUidActividadIsNotNull() {
            addCriterion("UID_ACTIVIDAD is not null");
            return (Criteria) this;
        }

        public Criteria andUidActividadEqualTo(String value) {
            addCriterion("UID_ACTIVIDAD =", value, "uidActividad");
            return (Criteria) this;
        }

        public Criteria andUidActividadNotEqualTo(String value) {
            addCriterion("UID_ACTIVIDAD <>", value, "uidActividad");
            return (Criteria) this;
        }

        public Criteria andUidActividadGreaterThan(String value) {
            addCriterion("UID_ACTIVIDAD >", value, "uidActividad");
            return (Criteria) this;
        }

        public Criteria andUidActividadGreaterThanOrEqualTo(String value) {
            addCriterion("UID_ACTIVIDAD >=", value, "uidActividad");
            return (Criteria) this;
        }

        public Criteria andUidActividadLessThan(String value) {
            addCriterion("UID_ACTIVIDAD <", value, "uidActividad");
            return (Criteria) this;
        }

        public Criteria andUidActividadLessThanOrEqualTo(String value) {
            addCriterion("UID_ACTIVIDAD <=", value, "uidActividad");
            return (Criteria) this;
        }

        public Criteria andUidActividadLike(String value) {
            addCriterion("UID_ACTIVIDAD like", value, "uidActividad");
            return (Criteria) this;
        }

        public Criteria andUidActividadNotLike(String value) {
            addCriterion("UID_ACTIVIDAD not like", value, "uidActividad");
            return (Criteria) this;
        }

        public Criteria andUidActividadIn(List<String> values) {
            addCriterion("UID_ACTIVIDAD in", values, "uidActividad");
            return (Criteria) this;
        }

        public Criteria andUidActividadNotIn(List<String> values) {
            addCriterion("UID_ACTIVIDAD not in", values, "uidActividad");
            return (Criteria) this;
        }

        public Criteria andUidActividadBetween(String value1, String value2) {
            addCriterion("UID_ACTIVIDAD between", value1, value2, "uidActividad");
            return (Criteria) this;
        }

        public Criteria andUidActividadNotBetween(String value1, String value2) {
            addCriterion("UID_ACTIVIDAD not between", value1, value2, "uidActividad");
            return (Criteria) this;
        }

        public Criteria andCodClienteIsNull() {
            addCriterion("CODCLI is null");
            return (Criteria) this;
        }

        public Criteria andCodClienteIsNotNull() {
            addCriterion("CODCLI is not null");
            return (Criteria) this;
        }

        public Criteria andCodClienteEqualTo(String value) {
            addCriterion("CODCLI =", value, "codCliente");
            return (Criteria) this;
        }

        public Criteria andCodClienteNotEqualTo(String value) {
            addCriterion("CODCLI <>", value, "codCliente");
            return (Criteria) this;
        }

        public Criteria andCodClienteGreaterThan(String value) {
            addCriterion("CODCLI >", value, "codCliente");
            return (Criteria) this;
        }

        public Criteria andCodClienteGreaterThanOrEqualTo(String value) {
            addCriterion("CODCLI >=", value, "codCliente");
            return (Criteria) this;
        }

        public Criteria andCodClienteLessThan(String value) {
            addCriterion("CODCLI <", value, "codCliente");
            return (Criteria) this;
        }

        public Criteria andCodClienteLessThanOrEqualTo(String value) {
            addCriterion("CODCLI <=", value, "codCliente");
            return (Criteria) this;
        }

        public Criteria andCodClienteLike(String value) {
            addCriterion("CODCLI like", value, "codCliente");
            return (Criteria) this;
        }

        public Criteria andCodClienteNotLike(String value) {
            addCriterion("CODCLI not like", value, "codCliente");
            return (Criteria) this;
        }

        public Criteria andCodClienteIn(List<String> values) {
            addCriterion("CODCLI in", values, "codCliente");
            return (Criteria) this;
        }

        public Criteria andCodClienteNotIn(List<String> values) {
            addCriterion("CODCLI not in", values, "codCliente");
            return (Criteria) this;
        }

        public Criteria andCodClienteBetween(String value1, String value2) {
            addCriterion("CODCLI between", value1, value2, "codCliente");
            return (Criteria) this;
        }

        public Criteria andCodClienteNotBetween(String value1, String value2) {
            addCriterion("CODCLI not between", value1, value2, "codCliente");
            return (Criteria) this;
        }

        public Criteria andDesClienteIsNull() {
            addCriterion("DESCLI is null");
            return (Criteria) this;
        }

        public Criteria andDesClienteIsNotNull() {
            addCriterion("DESCLI is not null");
            return (Criteria) this;
        }

        public Criteria andDesClienteEqualTo(String value) {
            addCriterion("DESCLI =", value, "desCliente");
            return (Criteria) this;
        }

        public Criteria andDesClienteNotEqualTo(String value) {
            addCriterion("DESCLI <>", value, "desCliente");
            return (Criteria) this;
        }

        public Criteria andDesClienteGreaterThan(String value) {
            addCriterion("DESCLI >", value, "desCliente");
            return (Criteria) this;
        }

        public Criteria andDesClienteGreaterThanOrEqualTo(String value) {
            addCriterion("DESCLI >=", value, "desCliente");
            return (Criteria) this;
        }

        public Criteria andDesClienteLessThan(String value) {
            addCriterion("DESCLI <", value, "desCliente");
            return (Criteria) this;
        }

        public Criteria andDesClienteLessThanOrEqualTo(String value) {
            addCriterion("DESCLI <=", value, "desCliente");
            return (Criteria) this;
        }

        public Criteria andDesClienteLike(String value) {
            addCriterion("DESCLI like", value, "desCliente");
            return (Criteria) this;
        }

        public Criteria andDesClienteNotLike(String value) {
            addCriterion("DESCLI not like", value, "desCliente");
            return (Criteria) this;
        }

        public Criteria andDesClienteIn(List<String> values) {
            addCriterion("DESCLI in", values, "desCliente");
            return (Criteria) this;
        }

        public Criteria andDesClienteNotIn(List<String> values) {
            addCriterion("DESCLI not in", values, "desCliente");
            return (Criteria) this;
        }

        public Criteria andDesClienteBetween(String value1, String value2) {
            addCriterion("DESCLI between", value1, value2, "desCliente");
            return (Criteria) this;
        }

        public Criteria andDesClienteNotBetween(String value1, String value2) {
            addCriterion("DESCLI not between", value1, value2, "desCliente");
            return (Criteria) this;
        }

        public Criteria andNombreComercialIsNull() {
            addCriterion("NOMBRE_COMERCIAL is null");
            return (Criteria) this;
        }

        public Criteria andNombreComercialIsNotNull() {
            addCriterion("NOMBRE_COMERCIAL is not null");
            return (Criteria) this;
        }

        public Criteria andNombreComercialEqualTo(String value) {
            addCriterion("NOMBRE_COMERCIAL =", value, "nombreComercial");
            return (Criteria) this;
        }

        public Criteria andNombreComercialNotEqualTo(String value) {
            addCriterion("NOMBRE_COMERCIAL <>", value, "nombreComercial");
            return (Criteria) this;
        }

        public Criteria andNombreComercialGreaterThan(String value) {
            addCriterion("NOMBRE_COMERCIAL >", value, "nombreComercial");
            return (Criteria) this;
        }

        public Criteria andNombreComercialGreaterThanOrEqualTo(String value) {
            addCriterion("NOMBRE_COMERCIAL >=", value, "nombreComercial");
            return (Criteria) this;
        }

        public Criteria andNombreComercialLessThan(String value) {
            addCriterion("NOMBRE_COMERCIAL <", value, "nombreComercial");
            return (Criteria) this;
        }

        public Criteria andNombreComercialLessThanOrEqualTo(String value) {
            addCriterion("NOMBRE_COMERCIAL <=", value, "nombreComercial");
            return (Criteria) this;
        }

        public Criteria andNombreComercialLike(String value) {
            addCriterion("NOMBRE_COMERCIAL like", value, "nombreComercial");
            return (Criteria) this;
        }

        public Criteria andNombreComercialNotLike(String value) {
            addCriterion("NOMBRE_COMERCIAL not like", value, "nombreComercial");
            return (Criteria) this;
        }

        public Criteria andNombreComercialIn(List<String> values) {
            addCriterion("NOMBRE_COMERCIAL in", values, "nombreComercial");
            return (Criteria) this;
        }

        public Criteria andNombreComercialNotIn(List<String> values) {
            addCriterion("NOMBRE_COMERCIAL not in", values, "nombreComercial");
            return (Criteria) this;
        }

        public Criteria andNombreComercialBetween(String value1, String value2) {
            addCriterion("NOMBRE_COMERCIAL between", value1, value2, "nombreComercial");
            return (Criteria) this;
        }

        public Criteria andNombreComercialNotBetween(String value1, String value2) {
            addCriterion("NOMBRE_COMERCIAL not between", value1, value2, "nombreComercial");
            return (Criteria) this;
        }

        public Criteria andDomicilioIsNull() {
            addCriterion("DOMICILIO is null");
            return (Criteria) this;
        }

        public Criteria andDomicilioIsNotNull() {
            addCriterion("DOMICILIO is not null");
            return (Criteria) this;
        }

        public Criteria andDomicilioEqualTo(String value) {
            addCriterion("DOMICILIO =", value, "domicilio");
            return (Criteria) this;
        }

        public Criteria andDomicilioNotEqualTo(String value) {
            addCriterion("DOMICILIO <>", value, "domicilio");
            return (Criteria) this;
        }

        public Criteria andDomicilioGreaterThan(String value) {
            addCriterion("DOMICILIO >", value, "domicilio");
            return (Criteria) this;
        }

        public Criteria andDomicilioGreaterThanOrEqualTo(String value) {
            addCriterion("DOMICILIO >=", value, "domicilio");
            return (Criteria) this;
        }

        public Criteria andDomicilioLessThan(String value) {
            addCriterion("DOMICILIO <", value, "domicilio");
            return (Criteria) this;
        }

        public Criteria andDomicilioLessThanOrEqualTo(String value) {
            addCriterion("DOMICILIO <=", value, "domicilio");
            return (Criteria) this;
        }

        public Criteria andDomicilioLike(String value) {
            addCriterion("DOMICILIO like", value, "domicilio");
            return (Criteria) this;
        }

        public Criteria andDomicilioNotLike(String value) {
            addCriterion("DOMICILIO not like", value, "domicilio");
            return (Criteria) this;
        }

        public Criteria andDomicilioIn(List<String> values) {
            addCriterion("DOMICILIO in", values, "domicilio");
            return (Criteria) this;
        }

        public Criteria andDomicilioNotIn(List<String> values) {
            addCriterion("DOMICILIO not in", values, "domicilio");
            return (Criteria) this;
        }

        public Criteria andDomicilioBetween(String value1, String value2) {
            addCriterion("DOMICILIO between", value1, value2, "domicilio");
            return (Criteria) this;
        }

        public Criteria andDomicilioNotBetween(String value1, String value2) {
            addCriterion("DOMICILIO not between", value1, value2, "domicilio");
            return (Criteria) this;
        }

        public Criteria andPoblacionIsNull() {
            addCriterion("POBLACION is null");
            return (Criteria) this;
        }

        public Criteria andPoblacionIsNotNull() {
            addCriterion("POBLACION is not null");
            return (Criteria) this;
        }

        public Criteria andPoblacionEqualTo(String value) {
            addCriterion("POBLACION =", value, "poblacion");
            return (Criteria) this;
        }

        public Criteria andPoblacionNotEqualTo(String value) {
            addCriterion("POBLACION <>", value, "poblacion");
            return (Criteria) this;
        }

        public Criteria andPoblacionGreaterThan(String value) {
            addCriterion("POBLACION >", value, "poblacion");
            return (Criteria) this;
        }

        public Criteria andPoblacionGreaterThanOrEqualTo(String value) {
            addCriterion("POBLACION >=", value, "poblacion");
            return (Criteria) this;
        }

        public Criteria andPoblacionLessThan(String value) {
            addCriterion("POBLACION <", value, "poblacion");
            return (Criteria) this;
        }

        public Criteria andPoblacionLessThanOrEqualTo(String value) {
            addCriterion("POBLACION <=", value, "poblacion");
            return (Criteria) this;
        }

        public Criteria andPoblacionLike(String value) {
            addCriterion("POBLACION like", value, "poblacion");
            return (Criteria) this;
        }

        public Criteria andPoblacionNotLike(String value) {
            addCriterion("POBLACION not like", value, "poblacion");
            return (Criteria) this;
        }

        public Criteria andPoblacionIn(List<String> values) {
            addCriterion("POBLACION in", values, "poblacion");
            return (Criteria) this;
        }

        public Criteria andPoblacionNotIn(List<String> values) {
            addCriterion("POBLACION not in", values, "poblacion");
            return (Criteria) this;
        }

        public Criteria andPoblacionBetween(String value1, String value2) {
            addCriterion("POBLACION between", value1, value2, "poblacion");
            return (Criteria) this;
        }

        public Criteria andPoblacionNotBetween(String value1, String value2) {
            addCriterion("POBLACION not between", value1, value2, "poblacion");
            return (Criteria) this;
        }

        public Criteria andProvinciaIsNull() {
            addCriterion("PROVINCIA is null");
            return (Criteria) this;
        }

        public Criteria andProvinciaIsNotNull() {
            addCriterion("PROVINCIA is not null");
            return (Criteria) this;
        }

        public Criteria andProvinciaEqualTo(String value) {
            addCriterion("PROVINCIA =", value, "provincia");
            return (Criteria) this;
        }

        public Criteria andProvinciaNotEqualTo(String value) {
            addCriterion("PROVINCIA <>", value, "provincia");
            return (Criteria) this;
        }

        public Criteria andProvinciaGreaterThan(String value) {
            addCriterion("PROVINCIA >", value, "provincia");
            return (Criteria) this;
        }

        public Criteria andProvinciaGreaterThanOrEqualTo(String value) {
            addCriterion("PROVINCIA >=", value, "provincia");
            return (Criteria) this;
        }

        public Criteria andProvinciaLessThan(String value) {
            addCriterion("PROVINCIA <", value, "provincia");
            return (Criteria) this;
        }

        public Criteria andProvinciaLessThanOrEqualTo(String value) {
            addCriterion("PROVINCIA <=", value, "provincia");
            return (Criteria) this;
        }

        public Criteria andProvinciaLike(String value) {
            addCriterion("PROVINCIA like", value, "provincia");
            return (Criteria) this;
        }

        public Criteria andProvinciaNotLike(String value) {
            addCriterion("PROVINCIA not like", value, "provincia");
            return (Criteria) this;
        }

        public Criteria andProvinciaIn(List<String> values) {
            addCriterion("PROVINCIA in", values, "provincia");
            return (Criteria) this;
        }

        public Criteria andProvinciaNotIn(List<String> values) {
            addCriterion("PROVINCIA not in", values, "provincia");
            return (Criteria) this;
        }

        public Criteria andProvinciaBetween(String value1, String value2) {
            addCriterion("PROVINCIA between", value1, value2, "provincia");
            return (Criteria) this;
        }

        public Criteria andProvinciaNotBetween(String value1, String value2) {
            addCriterion("PROVINCIA not between", value1, value2, "provincia");
            return (Criteria) this;
        }

        public Criteria andCpIsNull() {
            addCriterion("CP is null");
            return (Criteria) this;
        }

        public Criteria andCpIsNotNull() {
            addCriterion("CP is not null");
            return (Criteria) this;
        }

        public Criteria andCpEqualTo(String value) {
            addCriterion("CP =", value, "cp");
            return (Criteria) this;
        }

        public Criteria andCpNotEqualTo(String value) {
            addCriterion("CP <>", value, "cp");
            return (Criteria) this;
        }

        public Criteria andCpGreaterThan(String value) {
            addCriterion("CP >", value, "cp");
            return (Criteria) this;
        }

        public Criteria andCpGreaterThanOrEqualTo(String value) {
            addCriterion("CP >=", value, "cp");
            return (Criteria) this;
        }

        public Criteria andCpLessThan(String value) {
            addCriterion("CP <", value, "cp");
            return (Criteria) this;
        }

        public Criteria andCpLessThanOrEqualTo(String value) {
            addCriterion("CP <=", value, "cp");
            return (Criteria) this;
        }

        public Criteria andCpLike(String value) {
            addCriterion("CP like", value, "cp");
            return (Criteria) this;
        }

        public Criteria andCpNotLike(String value) {
            addCriterion("CP not like", value, "cp");
            return (Criteria) this;
        }

        public Criteria andCpIn(List<String> values) {
            addCriterion("CP in", values, "cp");
            return (Criteria) this;
        }

        public Criteria andCpNotIn(List<String> values) {
            addCriterion("CP not in", values, "cp");
            return (Criteria) this;
        }

        public Criteria andCpBetween(String value1, String value2) {
            addCriterion("CP between", value1, value2, "cp");
            return (Criteria) this;
        }

        public Criteria andCpNotBetween(String value1, String value2) {
            addCriterion("CP not between", value1, value2, "cp");
            return (Criteria) this;
        }

        public Criteria andTelefono1IsNull() {
            addCriterion("TELEFONO1 is null");
            return (Criteria) this;
        }

        public Criteria andTelefono1IsNotNull() {
            addCriterion("TELEFONO1 is not null");
            return (Criteria) this;
        }

        public Criteria andTelefono1EqualTo(String value) {
            addCriterion("TELEFONO1 =", value, "telefono1");
            return (Criteria) this;
        }

        public Criteria andTelefono1NotEqualTo(String value) {
            addCriterion("TELEFONO1 <>", value, "telefono1");
            return (Criteria) this;
        }

        public Criteria andTelefono1GreaterThan(String value) {
            addCriterion("TELEFONO1 >", value, "telefono1");
            return (Criteria) this;
        }

        public Criteria andTelefono1GreaterThanOrEqualTo(String value) {
            addCriterion("TELEFONO1 >=", value, "telefono1");
            return (Criteria) this;
        }

        public Criteria andTelefono1LessThan(String value) {
            addCriterion("TELEFONO1 <", value, "telefono1");
            return (Criteria) this;
        }

        public Criteria andTelefono1LessThanOrEqualTo(String value) {
            addCriterion("TELEFONO1 <=", value, "telefono1");
            return (Criteria) this;
        }

        public Criteria andTelefono1Like(String value) {
            addCriterion("TELEFONO1 like", value, "telefono1");
            return (Criteria) this;
        }

        public Criteria andTelefono1NotLike(String value) {
            addCriterion("TELEFONO1 not like", value, "telefono1");
            return (Criteria) this;
        }

        public Criteria andTelefono1In(List<String> values) {
            addCriterion("TELEFONO1 in", values, "telefono1");
            return (Criteria) this;
        }

        public Criteria andTelefono1NotIn(List<String> values) {
            addCriterion("TELEFONO1 not in", values, "telefono1");
            return (Criteria) this;
        }

        public Criteria andTelefono1Between(String value1, String value2) {
            addCriterion("TELEFONO1 between", value1, value2, "telefono1");
            return (Criteria) this;
        }

        public Criteria andTelefono1NotBetween(String value1, String value2) {
            addCriterion("TELEFONO1 not between", value1, value2, "telefono1");
            return (Criteria) this;
        }

        public Criteria andTelefono2IsNull() {
            addCriterion("TELEFONO2 is null");
            return (Criteria) this;
        }

        public Criteria andTelefono2IsNotNull() {
            addCriterion("TELEFONO2 is not null");
            return (Criteria) this;
        }

        public Criteria andTelefono2EqualTo(String value) {
            addCriterion("TELEFONO2 =", value, "telefono2");
            return (Criteria) this;
        }

        public Criteria andTelefono2NotEqualTo(String value) {
            addCriterion("TELEFONO2 <>", value, "telefono2");
            return (Criteria) this;
        }

        public Criteria andTelefono2GreaterThan(String value) {
            addCriterion("TELEFONO2 >", value, "telefono2");
            return (Criteria) this;
        }

        public Criteria andTelefono2GreaterThanOrEqualTo(String value) {
            addCriterion("TELEFONO2 >=", value, "telefono2");
            return (Criteria) this;
        }

        public Criteria andTelefono2LessThan(String value) {
            addCriterion("TELEFONO2 <", value, "telefono2");
            return (Criteria) this;
        }

        public Criteria andTelefono2LessThanOrEqualTo(String value) {
            addCriterion("TELEFONO2 <=", value, "telefono2");
            return (Criteria) this;
        }

        public Criteria andTelefono2Like(String value) {
            addCriterion("TELEFONO2 like", value, "telefono2");
            return (Criteria) this;
        }

        public Criteria andTelefono2NotLike(String value) {
            addCriterion("TELEFONO2 not like", value, "telefono2");
            return (Criteria) this;
        }

        public Criteria andTelefono2In(List<String> values) {
            addCriterion("TELEFONO2 in", values, "telefono2");
            return (Criteria) this;
        }

        public Criteria andTelefono2NotIn(List<String> values) {
            addCriterion("TELEFONO2 not in", values, "telefono2");
            return (Criteria) this;
        }

        public Criteria andTelefono2Between(String value1, String value2) {
            addCriterion("TELEFONO2 between", value1, value2, "telefono2");
            return (Criteria) this;
        }

        public Criteria andTelefono2NotBetween(String value1, String value2) {
            addCriterion("TELEFONO2 not between", value1, value2, "telefono2");
            return (Criteria) this;
        }

        public Criteria andFaxIsNull() {
            addCriterion("FAX is null");
            return (Criteria) this;
        }

        public Criteria andFaxIsNotNull() {
            addCriterion("FAX is not null");
            return (Criteria) this;
        }

        public Criteria andFaxEqualTo(String value) {
            addCriterion("FAX =", value, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxNotEqualTo(String value) {
            addCriterion("FAX <>", value, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxGreaterThan(String value) {
            addCriterion("FAX >", value, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxGreaterThanOrEqualTo(String value) {
            addCriterion("FAX >=", value, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxLessThan(String value) {
            addCriterion("FAX <", value, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxLessThanOrEqualTo(String value) {
            addCriterion("FAX <=", value, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxLike(String value) {
            addCriterion("FAX like", value, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxNotLike(String value) {
            addCriterion("FAX not like", value, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxIn(List<String> values) {
            addCriterion("FAX in", values, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxNotIn(List<String> values) {
            addCriterion("FAX not in", values, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxBetween(String value1, String value2) {
            addCriterion("FAX between", value1, value2, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxNotBetween(String value1, String value2) {
            addCriterion("FAX not between", value1, value2, "fax");
            return (Criteria) this;
        }

        public Criteria andCodpaisIsNull() {
            addCriterion("CODPAIS is null");
            return (Criteria) this;
        }

        public Criteria andCodpaisIsNotNull() {
            addCriterion("CODPAIS is not null");
            return (Criteria) this;
        }

        public Criteria andCodpaisEqualTo(String value) {
            addCriterion("CODPAIS =", value, "codpais");
            return (Criteria) this;
        }

        public Criteria andCodpaisNotEqualTo(String value) {
            addCriterion("CODPAIS <>", value, "codpais");
            return (Criteria) this;
        }

        public Criteria andCodpaisGreaterThan(String value) {
            addCriterion("CODPAIS >", value, "codpais");
            return (Criteria) this;
        }

        public Criteria andCodpaisGreaterThanOrEqualTo(String value) {
            addCriterion("CODPAIS >=", value, "codpais");
            return (Criteria) this;
        }

        public Criteria andCodpaisLessThan(String value) {
            addCriterion("CODPAIS <", value, "codpais");
            return (Criteria) this;
        }

        public Criteria andCodpaisLessThanOrEqualTo(String value) {
            addCriterion("CODPAIS <=", value, "codpais");
            return (Criteria) this;
        }

        public Criteria andCodpaisLike(String value) {
            addCriterion("CODPAIS like", value, "codpais");
            return (Criteria) this;
        }

        public Criteria andCodpaisNotLike(String value) {
            addCriterion("CODPAIS not like", value, "codpais");
            return (Criteria) this;
        }

        public Criteria andCodpaisIn(List<String> values) {
            addCriterion("CODPAIS in", values, "codpais");
            return (Criteria) this;
        }

        public Criteria andCodpaisNotIn(List<String> values) {
            addCriterion("CODPAIS not in", values, "codpais");
            return (Criteria) this;
        }

        public Criteria andCodpaisBetween(String value1, String value2) {
            addCriterion("CODPAIS between", value1, value2, "codpais");
            return (Criteria) this;
        }

        public Criteria andCodpaisNotBetween(String value1, String value2) {
            addCriterion("CODPAIS not between", value1, value2, "codpais");
            return (Criteria) this;
        }

        public Criteria andPersonaContactoIsNull() {
            addCriterion("PERSONA_CONTACTO is null");
            return (Criteria) this;
        }

        public Criteria andPersonaContactoIsNotNull() {
            addCriterion("PERSONA_CONTACTO is not null");
            return (Criteria) this;
        }

        public Criteria andPersonaContactoEqualTo(String value) {
            addCriterion("PERSONA_CONTACTO =", value, "personaContacto");
            return (Criteria) this;
        }

        public Criteria andPersonaContactoNotEqualTo(String value) {
            addCriterion("PERSONA_CONTACTO <>", value, "personaContacto");
            return (Criteria) this;
        }

        public Criteria andPersonaContactoGreaterThan(String value) {
            addCriterion("PERSONA_CONTACTO >", value, "personaContacto");
            return (Criteria) this;
        }

        public Criteria andPersonaContactoGreaterThanOrEqualTo(String value) {
            addCriterion("PERSONA_CONTACTO >=", value, "personaContacto");
            return (Criteria) this;
        }

        public Criteria andPersonaContactoLessThan(String value) {
            addCriterion("PERSONA_CONTACTO <", value, "personaContacto");
            return (Criteria) this;
        }

        public Criteria andPersonaContactoLessThanOrEqualTo(String value) {
            addCriterion("PERSONA_CONTACTO <=", value, "personaContacto");
            return (Criteria) this;
        }

        public Criteria andPersonaContactoLike(String value) {
            addCriterion("PERSONA_CONTACTO like", value, "personaContacto");
            return (Criteria) this;
        }

        public Criteria andPersonaContactoNotLike(String value) {
            addCriterion("PERSONA_CONTACTO not like", value, "personaContacto");
            return (Criteria) this;
        }

        public Criteria andPersonaContactoIn(List<String> values) {
            addCriterion("PERSONA_CONTACTO in", values, "personaContacto");
            return (Criteria) this;
        }

        public Criteria andPersonaContactoNotIn(List<String> values) {
            addCriterion("PERSONA_CONTACTO not in", values, "personaContacto");
            return (Criteria) this;
        }

        public Criteria andPersonaContactoBetween(String value1, String value2) {
            addCriterion("PERSONA_CONTACTO between", value1, value2, "personaContacto");
            return (Criteria) this;
        }

        public Criteria andPersonaContactoNotBetween(String value1, String value2) {
            addCriterion("PERSONA_CONTACTO not between", value1, value2, "personaContacto");
            return (Criteria) this;
        }

        public Criteria andEmailIsNull() {
            addCriterion("EMAIL is null");
            return (Criteria) this;
        }

        public Criteria andEmailIsNotNull() {
            addCriterion("EMAIL is not null");
            return (Criteria) this;
        }

        public Criteria andEmailEqualTo(String value) {
            addCriterion("EMAIL =", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotEqualTo(String value) {
            addCriterion("EMAIL <>", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThan(String value) {
            addCriterion("EMAIL >", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThanOrEqualTo(String value) {
            addCriterion("EMAIL >=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThan(String value) {
            addCriterion("EMAIL <", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThanOrEqualTo(String value) {
            addCriterion("EMAIL <=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLike(String value) {
            addCriterion("EMAIL like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotLike(String value) {
            addCriterion("EMAIL not like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailIn(List<String> values) {
            addCriterion("EMAIL in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotIn(List<String> values) {
            addCriterion("EMAIL not in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailBetween(String value1, String value2) {
            addCriterion("EMAIL between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotBetween(String value1, String value2) {
            addCriterion("EMAIL not between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andCifIsNull() {
            addCriterion("CIF is null");
            return (Criteria) this;
        }

        public Criteria andCifIsNotNull() {
            addCriterion("CIF is not null");
            return (Criteria) this;
        }

        public Criteria andCifEqualTo(String value) {
            addCriterion("CIF =", value, "cif");
            return (Criteria) this;
        }

        public Criteria andCifNotEqualTo(String value) {
            addCriterion("CIF <>", value, "cif");
            return (Criteria) this;
        }

        public Criteria andCifGreaterThan(String value) {
            addCriterion("CIF >", value, "cif");
            return (Criteria) this;
        }

        public Criteria andCifGreaterThanOrEqualTo(String value) {
            addCriterion("CIF >=", value, "cif");
            return (Criteria) this;
        }

        public Criteria andCifLessThan(String value) {
            addCriterion("CIF <", value, "cif");
            return (Criteria) this;
        }

        public Criteria andCifLessThanOrEqualTo(String value) {
            addCriterion("CIF <=", value, "cif");
            return (Criteria) this;
        }

        public Criteria andCifLike(String value) {
            addCriterion("CIF like", value, "cif");
            return (Criteria) this;
        }

        public Criteria andCifNotLike(String value) {
            addCriterion("CIF not like", value, "cif");
            return (Criteria) this;
        }

        public Criteria andCifIn(List<String> values) {
            addCriterion("CIF in", values, "cif");
            return (Criteria) this;
        }

        public Criteria andCifNotIn(List<String> values) {
            addCriterion("CIF not in", values, "cif");
            return (Criteria) this;
        }

        public Criteria andCifBetween(String value1, String value2) {
            addCriterion("CIF between", value1, value2, "cif");
            return (Criteria) this;
        }

        public Criteria andCifNotBetween(String value1, String value2) {
            addCriterion("CIF not between", value1, value2, "cif");
            return (Criteria) this;
        }

        public Criteria andIdTratImpuestosIsNull() {
            addCriterion("ID_TRAT_IMPUESTOS is null");
            return (Criteria) this;
        }

        public Criteria andIdTratImpuestosIsNotNull() {
            addCriterion("ID_TRAT_IMPUESTOS is not null");
            return (Criteria) this;
        }

        public Criteria andIdTratImpuestosEqualTo(Long value) {
            addCriterion("ID_TRAT_IMPUESTOS =", value, "idTratImpuestos");
            return (Criteria) this;
        }

        public Criteria andIdTratImpuestosNotEqualTo(Long value) {
            addCriterion("ID_TRAT_IMPUESTOS <>", value, "idTratImpuestos");
            return (Criteria) this;
        }

        public Criteria andIdTratImpuestosGreaterThan(Long value) {
            addCriterion("ID_TRAT_IMPUESTOS >", value, "idTratImpuestos");
            return (Criteria) this;
        }

        public Criteria andIdTratImpuestosGreaterThanOrEqualTo(Long value) {
            addCriterion("ID_TRAT_IMPUESTOS >=", value, "idTratImpuestos");
            return (Criteria) this;
        }

        public Criteria andIdTratImpuestosLessThan(Long value) {
            addCriterion("ID_TRAT_IMPUESTOS <", value, "idTratImpuestos");
            return (Criteria) this;
        }

        public Criteria andIdTratImpuestosLessThanOrEqualTo(Long value) {
            addCriterion("ID_TRAT_IMPUESTOS <=", value, "idTratImpuestos");
            return (Criteria) this;
        }

        public Criteria andIdTratImpuestosIn(List<Long> values) {
            addCriterion("ID_TRAT_IMPUESTOS in", values, "idTratImpuestos");
            return (Criteria) this;
        }

        public Criteria andIdTratImpuestosNotIn(List<Long> values) {
            addCriterion("ID_TRAT_IMPUESTOS not in", values, "idTratImpuestos");
            return (Criteria) this;
        }

        public Criteria andIdTratImpuestosBetween(Long value1, Long value2) {
            addCriterion("ID_TRAT_IMPUESTOS between", value1, value2, "idTratImpuestos");
            return (Criteria) this;
        }

        public Criteria andIdTratImpuestosNotBetween(Long value1, Long value2) {
            addCriterion("ID_TRAT_IMPUESTOS not between", value1, value2, "idTratImpuestos");
            return (Criteria) this;
        }

        public Criteria andIdMedioPagoVencimientoIsNull() {
            addCriterion("ID_MEDPAG_VEN is null");
            return (Criteria) this;
        }

        public Criteria andIdMedioPagoVencimientoIsNotNull() {
            addCriterion("ID_MEDPAG_VEN is not null");
            return (Criteria) this;
        }

        public Criteria andIdMedioPagoVencimientoEqualTo(Long value) {
            addCriterion("ID_MEDPAG_VEN =", value, "IdMedioPagoVencimiento");
            return (Criteria) this;
        }

        public Criteria andIdMedioPagoVencimientoNotEqualTo(Long value) {
            addCriterion("ID_MEDPAG_VEN <>", value, "IdMedioPagoVencimiento");
            return (Criteria) this;
        }

        public Criteria andIdMedioPagoVencimientoGreaterThan(Long value) {
            addCriterion("ID_MEDPAG_VEN >", value, "IdMedioPagoVencimiento");
            return (Criteria) this;
        }

        public Criteria andIdMedioPagoVencimientoGreaterThanOrEqualTo(Long value) {
            addCriterion("ID_MEDPAG_VEN >=", value, "IdMedioPagoVencimiento");
            return (Criteria) this;
        }

        public Criteria andIdMedioPagoVencimientoLessThan(Long value) {
            addCriterion("ID_MEDPAG_VEN <", value, "IdMedioPagoVencimiento");
            return (Criteria) this;
        }

        public Criteria andIdMedioPagoVencimientoLessThanOrEqualTo(Long value) {
            addCriterion("ID_MEDPAG_VEN <=", value, "IdMedioPagoVencimiento");
            return (Criteria) this;
        }

        public Criteria andIdMedioPagoVencimientoIn(List<Long> values) {
            addCriterion("ID_MEDPAG_VEN in", values, "IdMedioPagoVencimiento");
            return (Criteria) this;
        }

        public Criteria andIdMedioPagoVencimientoNotIn(List<Long> values) {
            addCriterion("ID_MEDPAG_VEN not in", values, "IdMedioPagoVencimiento");
            return (Criteria) this;
        }

        public Criteria andIdMedioPagoVencimientoBetween(Long value1, Long value2) {
            addCriterion("ID_MEDPAG_VEN between", value1, value2, "IdMedioPagoVencimiento");
            return (Criteria) this;
        }

        public Criteria andIdMedioPagoVencimientoNotBetween(Long value1, Long value2) {
            addCriterion("ID_MEDPAG_VEN not between", value1, value2, "IdMedioPagoVencimiento");
            return (Criteria) this;
        }

        public Criteria andCodtarIsNull() {
            addCriterion("CODTAR is null");
            return (Criteria) this;
        }

        public Criteria andCodtarIsNotNull() {
            addCriterion("CODTAR is not null");
            return (Criteria) this;
        }

        public Criteria andCodtarEqualTo(String value) {
            addCriterion("CODTAR =", value, "codtar");
            return (Criteria) this;
        }

        public Criteria andCodtarNotEqualTo(String value) {
            addCriterion("CODTAR <>", value, "codtar");
            return (Criteria) this;
        }

        public Criteria andCodtarGreaterThan(String value) {
            addCriterion("CODTAR >", value, "codtar");
            return (Criteria) this;
        }

        public Criteria andCodtarGreaterThanOrEqualTo(String value) {
            addCriterion("CODTAR >=", value, "codtar");
            return (Criteria) this;
        }

        public Criteria andCodtarLessThan(String value) {
            addCriterion("CODTAR <", value, "codtar");
            return (Criteria) this;
        }

        public Criteria andCodtarLessThanOrEqualTo(String value) {
            addCriterion("CODTAR <=", value, "codtar");
            return (Criteria) this;
        }

        public Criteria andCodtarLike(String value) {
            addCriterion("CODTAR like", value, "codtar");
            return (Criteria) this;
        }

        public Criteria andCodtarNotLike(String value) {
            addCriterion("CODTAR not like", value, "codtar");
            return (Criteria) this;
        }

        public Criteria andCodtarIn(List<String> values) {
            addCriterion("CODTAR in", values, "codtar");
            return (Criteria) this;
        }

        public Criteria andCodtarNotIn(List<String> values) {
            addCriterion("CODTAR not in", values, "codtar");
            return (Criteria) this;
        }

        public Criteria andCodtarBetween(String value1, String value2) {
            addCriterion("CODTAR between", value1, value2, "codtar");
            return (Criteria) this;
        }

        public Criteria andCodtarNotBetween(String value1, String value2) {
            addCriterion("CODTAR not between", value1, value2, "codtar");
            return (Criteria) this;
        }

        public Criteria andCodsecIsNull() {
            addCriterion("CODSEC is null");
            return (Criteria) this;
        }

        public Criteria andCodsecIsNotNull() {
            addCriterion("CODSEC is not null");
            return (Criteria) this;
        }

        public Criteria andCodsecEqualTo(String value) {
            addCriterion("CODSEC =", value, "codsec");
            return (Criteria) this;
        }

        public Criteria andCodsecNotEqualTo(String value) {
            addCriterion("CODSEC <>", value, "codsec");
            return (Criteria) this;
        }

        public Criteria andCodsecGreaterThan(String value) {
            addCriterion("CODSEC >", value, "codsec");
            return (Criteria) this;
        }

        public Criteria andCodsecGreaterThanOrEqualTo(String value) {
            addCriterion("CODSEC >=", value, "codsec");
            return (Criteria) this;
        }

        public Criteria andCodsecLessThan(String value) {
            addCriterion("CODSEC <", value, "codsec");
            return (Criteria) this;
        }

        public Criteria andCodsecLessThanOrEqualTo(String value) {
            addCriterion("CODSEC <=", value, "codsec");
            return (Criteria) this;
        }

        public Criteria andCodsecLike(String value) {
            addCriterion("CODSEC like", value, "codsec");
            return (Criteria) this;
        }

        public Criteria andCodsecNotLike(String value) {
            addCriterion("CODSEC not like", value, "codsec");
            return (Criteria) this;
        }

        public Criteria andCodsecIn(List<String> values) {
            addCriterion("CODSEC in", values, "codsec");
            return (Criteria) this;
        }

        public Criteria andCodsecNotIn(List<String> values) {
            addCriterion("CODSEC not in", values, "codsec");
            return (Criteria) this;
        }

        public Criteria andCodsecBetween(String value1, String value2) {
            addCriterion("CODSEC between", value1, value2, "codsec");
            return (Criteria) this;
        }

        public Criteria andCodsecNotBetween(String value1, String value2) {
            addCriterion("CODSEC not between", value1, value2, "codsec");
            return (Criteria) this;
        }

        public Criteria andBancoIsNull() {
            addCriterion("BANCO is null");
            return (Criteria) this;
        }

        public Criteria andBancoIsNotNull() {
            addCriterion("BANCO is not null");
            return (Criteria) this;
        }

        public Criteria andBancoEqualTo(String value) {
            addCriterion("BANCO =", value, "banco");
            return (Criteria) this;
        }

        public Criteria andBancoNotEqualTo(String value) {
            addCriterion("BANCO <>", value, "banco");
            return (Criteria) this;
        }

        public Criteria andBancoGreaterThan(String value) {
            addCriterion("BANCO >", value, "banco");
            return (Criteria) this;
        }

        public Criteria andBancoGreaterThanOrEqualTo(String value) {
            addCriterion("BANCO >=", value, "banco");
            return (Criteria) this;
        }

        public Criteria andBancoLessThan(String value) {
            addCriterion("BANCO <", value, "banco");
            return (Criteria) this;
        }

        public Criteria andBancoLessThanOrEqualTo(String value) {
            addCriterion("BANCO <=", value, "banco");
            return (Criteria) this;
        }

        public Criteria andBancoLike(String value) {
            addCriterion("BANCO like", value, "banco");
            return (Criteria) this;
        }

        public Criteria andBancoNotLike(String value) {
            addCriterion("BANCO not like", value, "banco");
            return (Criteria) this;
        }

        public Criteria andBancoIn(List<String> values) {
            addCriterion("BANCO in", values, "banco");
            return (Criteria) this;
        }

        public Criteria andBancoNotIn(List<String> values) {
            addCriterion("BANCO not in", values, "banco");
            return (Criteria) this;
        }

        public Criteria andBancoBetween(String value1, String value2) {
            addCriterion("BANCO between", value1, value2, "banco");
            return (Criteria) this;
        }

        public Criteria andBancoNotBetween(String value1, String value2) {
            addCriterion("BANCO not between", value1, value2, "banco");
            return (Criteria) this;
        }

        public Criteria andBancoDomicilioIsNull() {
            addCriterion("BANCO_DOMICILIO is null");
            return (Criteria) this;
        }

        public Criteria andBancoDomicilioIsNotNull() {
            addCriterion("BANCO_DOMICILIO is not null");
            return (Criteria) this;
        }

        public Criteria andBancoDomicilioEqualTo(String value) {
            addCriterion("BANCO_DOMICILIO =", value, "bancoDomicilio");
            return (Criteria) this;
        }

        public Criteria andBancoDomicilioNotEqualTo(String value) {
            addCriterion("BANCO_DOMICILIO <>", value, "bancoDomicilio");
            return (Criteria) this;
        }

        public Criteria andBancoDomicilioGreaterThan(String value) {
            addCriterion("BANCO_DOMICILIO >", value, "bancoDomicilio");
            return (Criteria) this;
        }

        public Criteria andBancoDomicilioGreaterThanOrEqualTo(String value) {
            addCriterion("BANCO_DOMICILIO >=", value, "bancoDomicilio");
            return (Criteria) this;
        }

        public Criteria andBancoDomicilioLessThan(String value) {
            addCriterion("BANCO_DOMICILIO <", value, "bancoDomicilio");
            return (Criteria) this;
        }

        public Criteria andBancoDomicilioLessThanOrEqualTo(String value) {
            addCriterion("BANCO_DOMICILIO <=", value, "bancoDomicilio");
            return (Criteria) this;
        }

        public Criteria andBancoDomicilioLike(String value) {
            addCriterion("BANCO_DOMICILIO like", value, "bancoDomicilio");
            return (Criteria) this;
        }

        public Criteria andBancoDomicilioNotLike(String value) {
            addCriterion("BANCO_DOMICILIO not like", value, "bancoDomicilio");
            return (Criteria) this;
        }

        public Criteria andBancoDomicilioIn(List<String> values) {
            addCriterion("BANCO_DOMICILIO in", values, "bancoDomicilio");
            return (Criteria) this;
        }

        public Criteria andBancoDomicilioNotIn(List<String> values) {
            addCriterion("BANCO_DOMICILIO not in", values, "bancoDomicilio");
            return (Criteria) this;
        }

        public Criteria andBancoDomicilioBetween(String value1, String value2) {
            addCriterion("BANCO_DOMICILIO between", value1, value2, "bancoDomicilio");
            return (Criteria) this;
        }

        public Criteria andBancoDomicilioNotBetween(String value1, String value2) {
            addCriterion("BANCO_DOMICILIO not between", value1, value2, "bancoDomicilio");
            return (Criteria) this;
        }

        public Criteria andBancoPoblacionIsNull() {
            addCriterion("BANCO_POBLACION is null");
            return (Criteria) this;
        }

        public Criteria andBancoPoblacionIsNotNull() {
            addCriterion("BANCO_POBLACION is not null");
            return (Criteria) this;
        }

        public Criteria andBancoPoblacionEqualTo(String value) {
            addCriterion("BANCO_POBLACION =", value, "bancoPoblacion");
            return (Criteria) this;
        }

        public Criteria andBancoPoblacionNotEqualTo(String value) {
            addCriterion("BANCO_POBLACION <>", value, "bancoPoblacion");
            return (Criteria) this;
        }

        public Criteria andBancoPoblacionGreaterThan(String value) {
            addCriterion("BANCO_POBLACION >", value, "bancoPoblacion");
            return (Criteria) this;
        }

        public Criteria andBancoPoblacionGreaterThanOrEqualTo(String value) {
            addCriterion("BANCO_POBLACION >=", value, "bancoPoblacion");
            return (Criteria) this;
        }

        public Criteria andBancoPoblacionLessThan(String value) {
            addCriterion("BANCO_POBLACION <", value, "bancoPoblacion");
            return (Criteria) this;
        }

        public Criteria andBancoPoblacionLessThanOrEqualTo(String value) {
            addCriterion("BANCO_POBLACION <=", value, "bancoPoblacion");
            return (Criteria) this;
        }

        public Criteria andBancoPoblacionLike(String value) {
            addCriterion("BANCO_POBLACION like", value, "bancoPoblacion");
            return (Criteria) this;
        }

        public Criteria andBancoPoblacionNotLike(String value) {
            addCriterion("BANCO_POBLACION not like", value, "bancoPoblacion");
            return (Criteria) this;
        }

        public Criteria andBancoPoblacionIn(List<String> values) {
            addCriterion("BANCO_POBLACION in", values, "bancoPoblacion");
            return (Criteria) this;
        }

        public Criteria andBancoPoblacionNotIn(List<String> values) {
            addCriterion("BANCO_POBLACION not in", values, "bancoPoblacion");
            return (Criteria) this;
        }

        public Criteria andBancoPoblacionBetween(String value1, String value2) {
            addCriterion("BANCO_POBLACION between", value1, value2, "bancoPoblacion");
            return (Criteria) this;
        }

        public Criteria andBancoPoblacionNotBetween(String value1, String value2) {
            addCriterion("BANCO_POBLACION not between", value1, value2, "bancoPoblacion");
            return (Criteria) this;
        }

        public Criteria andCccIsNull() {
            addCriterion("CCC is null");
            return (Criteria) this;
        }

        public Criteria andCccIsNotNull() {
            addCriterion("CCC is not null");
            return (Criteria) this;
        }

        public Criteria andCccEqualTo(String value) {
            addCriterion("CCC =", value, "ccc");
            return (Criteria) this;
        }

        public Criteria andCccNotEqualTo(String value) {
            addCriterion("CCC <>", value, "ccc");
            return (Criteria) this;
        }

        public Criteria andCccGreaterThan(String value) {
            addCriterion("CCC >", value, "ccc");
            return (Criteria) this;
        }

        public Criteria andCccGreaterThanOrEqualTo(String value) {
            addCriterion("CCC >=", value, "ccc");
            return (Criteria) this;
        }

        public Criteria andCccLessThan(String value) {
            addCriterion("CCC <", value, "ccc");
            return (Criteria) this;
        }

        public Criteria andCccLessThanOrEqualTo(String value) {
            addCriterion("CCC <=", value, "ccc");
            return (Criteria) this;
        }

        public Criteria andCccLike(String value) {
            addCriterion("CCC like", value, "ccc");
            return (Criteria) this;
        }

        public Criteria andCccNotLike(String value) {
            addCriterion("CCC not like", value, "ccc");
            return (Criteria) this;
        }

        public Criteria andCccIn(List<String> values) {
            addCriterion("CCC in", values, "ccc");
            return (Criteria) this;
        }

        public Criteria andCccNotIn(List<String> values) {
            addCriterion("CCC not in", values, "ccc");
            return (Criteria) this;
        }

        public Criteria andCccBetween(String value1, String value2) {
            addCriterion("CCC between", value1, value2, "ccc");
            return (Criteria) this;
        }

        public Criteria andCccNotBetween(String value1, String value2) {
            addCriterion("CCC not between", value1, value2, "ccc");
            return (Criteria) this;
        }

        public Criteria andObservacionesIsNull() {
            addCriterion("OBSERVACIONES is null");
            return (Criteria) this;
        }

        public Criteria andObservacionesIsNotNull() {
            addCriterion("OBSERVACIONES is not null");
            return (Criteria) this;
        }

        public Criteria andObservacionesEqualTo(String value) {
            addCriterion("OBSERVACIONES =", value, "observaciones");
            return (Criteria) this;
        }

        public Criteria andObservacionesNotEqualTo(String value) {
            addCriterion("OBSERVACIONES <>", value, "observaciones");
            return (Criteria) this;
        }

        public Criteria andObservacionesGreaterThan(String value) {
            addCriterion("OBSERVACIONES >", value, "observaciones");
            return (Criteria) this;
        }

        public Criteria andObservacionesGreaterThanOrEqualTo(String value) {
            addCriterion("OBSERVACIONES >=", value, "observaciones");
            return (Criteria) this;
        }

        public Criteria andObservacionesLessThan(String value) {
            addCriterion("OBSERVACIONES <", value, "observaciones");
            return (Criteria) this;
        }

        public Criteria andObservacionesLessThanOrEqualTo(String value) {
            addCriterion("OBSERVACIONES <=", value, "observaciones");
            return (Criteria) this;
        }

        public Criteria andObservacionesLike(String value) {
            addCriterion("OBSERVACIONES like", value, "observaciones");
            return (Criteria) this;
        }

        public Criteria andObservacionesNotLike(String value) {
            addCriterion("OBSERVACIONES not like", value, "observaciones");
            return (Criteria) this;
        }

        public Criteria andObservacionesIn(List<String> values) {
            addCriterion("OBSERVACIONES in", values, "observaciones");
            return (Criteria) this;
        }

        public Criteria andObservacionesNotIn(List<String> values) {
            addCriterion("OBSERVACIONES not in", values, "observaciones");
            return (Criteria) this;
        }

        public Criteria andObservacionesBetween(String value1, String value2) {
            addCriterion("OBSERVACIONES between", value1, value2, "observaciones");
            return (Criteria) this;
        }

        public Criteria andObservacionesNotBetween(String value1, String value2) {
            addCriterion("OBSERVACIONES not between", value1, value2, "observaciones");
            return (Criteria) this;
        }

        public Criteria andActivoIsNull() {
            addCriterion("ACTIVO is null");
            return (Criteria) this;
        }

        public Criteria andActivoIsNotNull() {
            addCriterion("ACTIVO is not null");
            return (Criteria) this;
        }

        public Criteria andActivoEqualTo(Boolean value) {
            addActivoCriterion("ACTIVO =", value, "activo");
            return (Criteria) this;
        }

        public Criteria andActivoNotEqualTo(Boolean value) {
            addActivoCriterion("ACTIVO <>", value, "activo");
            return (Criteria) this;
        }

        public Criteria andActivoGreaterThan(Boolean value) {
            addActivoCriterion("ACTIVO >", value, "activo");
            return (Criteria) this;
        }

        public Criteria andActivoGreaterThanOrEqualTo(Boolean value) {
            addActivoCriterion("ACTIVO >=", value, "activo");
            return (Criteria) this;
        }

        public Criteria andActivoLessThan(Boolean value) {
            addActivoCriterion("ACTIVO <", value, "activo");
            return (Criteria) this;
        }

        public Criteria andActivoLessThanOrEqualTo(Boolean value) {
            addActivoCriterion("ACTIVO <=", value, "activo");
            return (Criteria) this;
        }

        public Criteria andActivoLike(Boolean value) {
            addActivoCriterion("ACTIVO like", value, "activo");
            return (Criteria) this;
        }

        public Criteria andActivoNotLike(Boolean value) {
            addActivoCriterion("ACTIVO not like", value, "activo");
            return (Criteria) this;
        }

        public Criteria andActivoIn(List<Boolean> values) {
            addActivoCriterion("ACTIVO in", values, "activo");
            return (Criteria) this;
        }

        public Criteria andActivoNotIn(List<Boolean> values) {
            addActivoCriterion("ACTIVO not in", values, "activo");
            return (Criteria) this;
        }

        public Criteria andActivoBetween(Boolean value1, Boolean value2) {
            addActivoCriterion("ACTIVO between", value1, value2, "activo");
            return (Criteria) this;
        }

        public Criteria andActivoNotBetween(Boolean value1, Boolean value2) {
            addActivoCriterion("ACTIVO not between", value1, value2, "activo");
            return (Criteria) this;
        }

        public Criteria andFechaAltaIsNull() {
            addCriterion("FECHA_ALTA is null");
            return (Criteria) this;
        }

        public Criteria andFechaAltaIsNotNull() {
            addCriterion("FECHA_ALTA is not null");
            return (Criteria) this;
        }

        public Criteria andFechaAltaEqualTo(Date value) {
            addCriterion("FECHA_ALTA =", value, "fechaAlta");
            return (Criteria) this;
        }

        public Criteria andFechaAltaNotEqualTo(Date value) {
            addCriterion("FECHA_ALTA <>", value, "fechaAlta");
            return (Criteria) this;
        }

        public Criteria andFechaAltaGreaterThan(Date value) {
            addCriterion("FECHA_ALTA >", value, "fechaAlta");
            return (Criteria) this;
        }

        public Criteria andFechaAltaGreaterThanOrEqualTo(Date value) {
            addCriterion("FECHA_ALTA >=", value, "fechaAlta");
            return (Criteria) this;
        }

        public Criteria andFechaAltaLessThan(Date value) {
            addCriterion("FECHA_ALTA <", value, "fechaAlta");
            return (Criteria) this;
        }

        public Criteria andFechaAltaLessThanOrEqualTo(Date value) {
            addCriterion("FECHA_ALTA <=", value, "fechaAlta");
            return (Criteria) this;
        }

        public Criteria andFechaAltaIn(List<Date> values) {
            addCriterion("FECHA_ALTA in", values, "fechaAlta");
            return (Criteria) this;
        }

        public Criteria andFechaAltaNotIn(List<Date> values) {
            addCriterion("FECHA_ALTA not in", values, "fechaAlta");
            return (Criteria) this;
        }

        public Criteria andFechaAltaBetween(Date value1, Date value2) {
            addCriterion("FECHA_ALTA between", value1, value2, "fechaAlta");
            return (Criteria) this;
        }

        public Criteria andFechaAltaNotBetween(Date value1, Date value2) {
            addCriterion("FECHA_ALTA not between", value1, value2, "fechaAlta");
            return (Criteria) this;
        }

        public Criteria andFechaBajaIsNull() {
            addCriterion("FECHA_BAJA is null");
            return (Criteria) this;
        }

        public Criteria andFechaBajaIsNotNull() {
            addCriterion("FECHA_BAJA is not null");
            return (Criteria) this;
        }

        public Criteria andFechaBajaEqualTo(Date value) {
            addCriterion("FECHA_BAJA =", value, "fechaBaja");
            return (Criteria) this;
        }

        public Criteria andFechaBajaNotEqualTo(Date value) {
            addCriterion("FECHA_BAJA <>", value, "fechaBaja");
            return (Criteria) this;
        }

        public Criteria andFechaBajaGreaterThan(Date value) {
            addCriterion("FECHA_BAJA >", value, "fechaBaja");
            return (Criteria) this;
        }

        public Criteria andFechaBajaGreaterThanOrEqualTo(Date value) {
            addCriterion("FECHA_BAJA >=", value, "fechaBaja");
            return (Criteria) this;
        }

        public Criteria andFechaBajaLessThan(Date value) {
            addCriterion("FECHA_BAJA <", value, "fechaBaja");
            return (Criteria) this;
        }

        public Criteria andFechaBajaLessThanOrEqualTo(Date value) {
            addCriterion("FECHA_BAJA <=", value, "fechaBaja");
            return (Criteria) this;
        }

        public Criteria andFechaBajaIn(List<Date> values) {
            addCriterion("FECHA_BAJA in", values, "fechaBaja");
            return (Criteria) this;
        }

        public Criteria andFechaBajaNotIn(List<Date> values) {
            addCriterion("FECHA_BAJA not in", values, "fechaBaja");
            return (Criteria) this;
        }

        public Criteria andFechaBajaBetween(Date value1, Date value2) {
            addCriterion("FECHA_BAJA between", value1, value2, "fechaBaja");
            return (Criteria) this;
        }

        public Criteria andFechaBajaNotBetween(Date value1, Date value2) {
            addCriterion("FECHA_BAJA not between", value1, value2, "fechaBaja");
            return (Criteria) this;
        }

        public Criteria andRiesgoConcedidoIsNull() {
            addCriterion("RIESGO_CONCEDIDO is null");
            return (Criteria) this;
        }

        public Criteria andRiesgoConcedidoIsNotNull() {
            addCriterion("RIESGO_CONCEDIDO is not null");
            return (Criteria) this;
        }

        public Criteria andRiesgoConcedidoEqualTo(Integer value) {
            addCriterion("RIESGO_CONCEDIDO =", value, "riesgoConcedido");
            return (Criteria) this;
        }

        public Criteria andRiesgoConcedidoNotEqualTo(Integer value) {
            addCriterion("RIESGO_CONCEDIDO <>", value, "riesgoConcedido");
            return (Criteria) this;
        }

        public Criteria andRiesgoConcedidoGreaterThan(Integer value) {
            addCriterion("RIESGO_CONCEDIDO >", value, "riesgoConcedido");
            return (Criteria) this;
        }

        public Criteria andRiesgoConcedidoGreaterThanOrEqualTo(Integer value) {
            addCriterion("RIESGO_CONCEDIDO >=", value, "riesgoConcedido");
            return (Criteria) this;
        }

        public Criteria andRiesgoConcedidoLessThan(Integer value) {
            addCriterion("RIESGO_CONCEDIDO <", value, "riesgoConcedido");
            return (Criteria) this;
        }

        public Criteria andRiesgoConcedidoLessThanOrEqualTo(Integer value) {
            addCriterion("RIESGO_CONCEDIDO <=", value, "riesgoConcedido");
            return (Criteria) this;
        }

        public Criteria andRiesgoConcedidoIn(List<Integer> values) {
            addCriterion("RIESGO_CONCEDIDO in", values, "riesgoConcedido");
            return (Criteria) this;
        }

        public Criteria andRiesgoConcedidoNotIn(List<Integer> values) {
            addCriterion("RIESGO_CONCEDIDO not in", values, "riesgoConcedido");
            return (Criteria) this;
        }

        public Criteria andRiesgoConcedidoBetween(Integer value1, Integer value2) {
            addCriterion("RIESGO_CONCEDIDO between", value1, value2, "riesgoConcedido");
            return (Criteria) this;
        }

        public Criteria andRiesgoConcedidoNotBetween(Integer value1, Integer value2) {
            addCriterion("RIESGO_CONCEDIDO not between", value1, value2, "riesgoConcedido");
            return (Criteria) this;
        }

        public Criteria andCodcliFacturaIsNull() {
            addCriterion("CODCLI_FACTURA is null");
            return (Criteria) this;
        }

        public Criteria andCodcliFacturaIsNotNull() {
            addCriterion("CODCLI_FACTURA is not null");
            return (Criteria) this;
        }

        public Criteria andCodcliFacturaEqualTo(String value) {
            addCriterion("CODCLI_FACTURA =", value, "codcliFactura");
            return (Criteria) this;
        }

        public Criteria andCodcliFacturaNotEqualTo(String value) {
            addCriterion("CODCLI_FACTURA <>", value, "codcliFactura");
            return (Criteria) this;
        }

        public Criteria andCodcliFacturaGreaterThan(String value) {
            addCriterion("CODCLI_FACTURA >", value, "codcliFactura");
            return (Criteria) this;
        }

        public Criteria andCodcliFacturaGreaterThanOrEqualTo(String value) {
            addCriterion("CODCLI_FACTURA >=", value, "codcliFactura");
            return (Criteria) this;
        }

        public Criteria andCodcliFacturaLessThan(String value) {
            addCriterion("CODCLI_FACTURA <", value, "codcliFactura");
            return (Criteria) this;
        }

        public Criteria andCodcliFacturaLessThanOrEqualTo(String value) {
            addCriterion("CODCLI_FACTURA <=", value, "codcliFactura");
            return (Criteria) this;
        }

        public Criteria andCodcliFacturaLike(String value) {
            addCriterion("CODCLI_FACTURA like", value, "codcliFactura");
            return (Criteria) this;
        }

        public Criteria andCodcliFacturaNotLike(String value) {
            addCriterion("CODCLI_FACTURA not like", value, "codcliFactura");
            return (Criteria) this;
        }

        public Criteria andCodcliFacturaIn(List<String> values) {
            addCriterion("CODCLI_FACTURA in", values, "codcliFactura");
            return (Criteria) this;
        }

        public Criteria andCodcliFacturaNotIn(List<String> values) {
            addCriterion("CODCLI_FACTURA not in", values, "codcliFactura");
            return (Criteria) this;
        }

        public Criteria andCodcliFacturaBetween(String value1, String value2) {
            addCriterion("CODCLI_FACTURA between", value1, value2, "codcliFactura");
            return (Criteria) this;
        }

        public Criteria andCodcliFacturaNotBetween(String value1, String value2) {
            addCriterion("CODCLI_FACTURA not between", value1, value2, "codcliFactura");
            return (Criteria) this;
        }

        public Criteria andLocalidadIsNull() {
            addCriterion("LOCALIDAD is null");
            return (Criteria) this;
        }

        public Criteria andLocalidadIsNotNull() {
            addCriterion("LOCALIDAD is not null");
            return (Criteria) this;
        }

        public Criteria andLocalidadEqualTo(String value) {
            addCriterion("LOCALIDAD =", value, "localidad");
            return (Criteria) this;
        }

        public Criteria andLocalidadNotEqualTo(String value) {
            addCriterion("LOCALIDAD <>", value, "localidad");
            return (Criteria) this;
        }

        public Criteria andLocalidadGreaterThan(String value) {
            addCriterion("LOCALIDAD >", value, "localidad");
            return (Criteria) this;
        }

        public Criteria andLocalidadGreaterThanOrEqualTo(String value) {
            addCriterion("LOCALIDAD >=", value, "localidad");
            return (Criteria) this;
        }

        public Criteria andLocalidadLessThan(String value) {
            addCriterion("LOCALIDAD <", value, "localidad");
            return (Criteria) this;
        }

        public Criteria andLocalidadLessThanOrEqualTo(String value) {
            addCriterion("LOCALIDAD <=", value, "localidad");
            return (Criteria) this;
        }

        public Criteria andLocalidadLike(String value) {
            addCriterion("LOCALIDAD like", value, "localidad");
            return (Criteria) this;
        }

        public Criteria andLocalidadNotLike(String value) {
            addCriterion("LOCALIDAD not like", value, "localidad");
            return (Criteria) this;
        }

        public Criteria andLocalidadIn(List<String> values) {
            addCriterion("LOCALIDAD in", values, "localidad");
            return (Criteria) this;
        }

        public Criteria andLocalidadNotIn(List<String> values) {
            addCriterion("LOCALIDAD not in", values, "localidad");
            return (Criteria) this;
        }

        public Criteria andLocalidadBetween(String value1, String value2) {
            addCriterion("LOCALIDAD between", value1, value2, "localidad");
            return (Criteria) this;
        }

        public Criteria andLocalidadNotBetween(String value1, String value2) {
            addCriterion("LOCALIDAD not between", value1, value2, "localidad");
            return (Criteria) this;
        }

        public Criteria andCodtipoidenIsNull() {
            addCriterion("CODTIPOIDEN is null");
            return (Criteria) this;
        }

        public Criteria andCodtipoidenIsNotNull() {
            addCriterion("CODTIPOIDEN is not null");
            return (Criteria) this;
        }

        public Criteria andCodtipoidenEqualTo(String value) {
            addCriterion("CODTIPOIDEN =", value, "codtipoiden");
            return (Criteria) this;
        }

        public Criteria andCodtipoidenNotEqualTo(String value) {
            addCriterion("CODTIPOIDEN <>", value, "codtipoiden");
            return (Criteria) this;
        }

        public Criteria andCodtipoidenGreaterThan(String value) {
            addCriterion("CODTIPOIDEN >", value, "codtipoiden");
            return (Criteria) this;
        }

        public Criteria andCodtipoidenGreaterThanOrEqualTo(String value) {
            addCriterion("CODTIPOIDEN >=", value, "codtipoiden");
            return (Criteria) this;
        }

        public Criteria andCodtipoidenLessThan(String value) {
            addCriterion("CODTIPOIDEN <", value, "codtipoiden");
            return (Criteria) this;
        }

        public Criteria andCodtipoidenLessThanOrEqualTo(String value) {
            addCriterion("CODTIPOIDEN <=", value, "codtipoiden");
            return (Criteria) this;
        }

        public Criteria andCodtipoidenLike(String value) {
            addCriterion("CODTIPOIDEN like", value, "codtipoiden");
            return (Criteria) this;
        }

        public Criteria andCodtipoidenNotLike(String value) {
            addCriterion("CODTIPOIDEN not like", value, "codtipoiden");
            return (Criteria) this;
        }

        public Criteria andCodtipoidenIn(List<String> values) {
            addCriterion("CODTIPOIDEN in", values, "codtipoiden");
            return (Criteria) this;
        }

        public Criteria andCodtipoidenNotIn(List<String> values) {
            addCriterion("CODTIPOIDEN not in", values, "codtipoiden");
            return (Criteria) this;
        }

        public Criteria andCodtipoidenBetween(String value1, String value2) {
            addCriterion("CODTIPOIDEN between", value1, value2, "codtipoiden");
            return (Criteria) this;
        }

        public Criteria andCodtipoidenNotBetween(String value1, String value2) {
            addCriterion("CODTIPOIDEN not between", value1, value2, "codtipoiden");
            return (Criteria) this;
        }
        
        public Criteria andDepositoIsNull() {
            addCriterion("DEPOSITO is null");
            return (Criteria) this;
        }

        public Criteria andDepositoIsNotNull() {
            addCriterion("DEPOSITO is not null");
            return (Criteria) this;
        }

        public Criteria andDepositoEqualTo(String value) {
            addCriterion("DEPOSITO =", value, "deposito");
            return (Criteria) this;
        }

        public Criteria andDepositoNotEqualTo(String value) {
            addCriterion("DEPOSITO <>", value, "deposito");
            return (Criteria) this;
        }

        public Criteria andDepositoGreaterThan(String value) {
            addCriterion("DEPOSITO >", value, "deposito");
            return (Criteria) this;
        }

        public Criteria andDepositoGreaterThanOrEqualTo(String value) {
            addCriterion("DEPOSITO >=", value, "deposito");
            return (Criteria) this;
        }

        public Criteria andDepositoLessThan(String value) {
            addCriterion("DEPOSITO <", value, "deposito");
            return (Criteria) this;
        }

        public Criteria andDepositoLessThanOrEqualTo(String value) {
            addCriterion("DEPOSITO <=", value, "deposito");
            return (Criteria) this;
        }

        public Criteria andDepositoLike(String value) {
            addCriterion("DEPOSITO like", value, "deposito");
            return (Criteria) this;
        }

        public Criteria andDepositoNotLike(String value) {
            addCriterion("DEPOSITO not like", value, "deposito");
            return (Criteria) this;
        }

        public Criteria andDepositoIn(List<String> values) {
            addCriterion("DEPOSITO in", values, "deposito");
            return (Criteria) this;
        }

        public Criteria andDepositoNotIn(List<String> values) {
            addCriterion("DEPOSITO not in", values, "deposito");
            return (Criteria) this;
        }

        public Criteria andDepositoBetween(String value1, String value2) {
            addCriterion("DEPOSITO between", value1, value2, "deposito");
            return (Criteria) this;
        }

        public Criteria andDepositoNotBetween(String value1, String value2) {
            addCriterion("DEPOSITO not between", value1, value2, "deposito");
            return (Criteria) this;
        }
        
        public Criteria andCodlenguaIsNull() {
            addCriterion("CODLENGUA is null");
            return (Criteria) this;
        }

        public Criteria andCodlenguaIsNotNull() {
            addCriterion("CODLENGUA is not null");
            return (Criteria) this;
        }

        public Criteria andCodlenguaEqualTo(String value) {
            addCriterion("CODLENGUA =", value, "codlengua");
            return (Criteria) this;
        }

        public Criteria andCodlenguaNotEqualTo(String value) {
            addCriterion("CODLENGUA <>", value, "codlengua");
            return (Criteria) this;
        }

        public Criteria andCodlenguaGreaterThan(String value) {
            addCriterion("CODLENGUA >", value, "codlengua");
            return (Criteria) this;
        }

        public Criteria andCodlenguaGreaterThanOrEqualTo(String value) {
            addCriterion("CODLENGUA >=", value, "codlengua");
            return (Criteria) this;
        }

        public Criteria andCodlenguaLessThan(String value) {
            addCriterion("CODLENGUA <", value, "codlengua");
            return (Criteria) this;
        }

        public Criteria andCodlenguaLessThanOrEqualTo(String value) {
            addCriterion("CODLENGUA <=", value, "codlengua");
            return (Criteria) this;
        }

        public Criteria andCodlenguaLike(String value) {
            addCriterion("CODLENGUA like", value, "codlengua");
            return (Criteria) this;
        }

        public Criteria andCodlenguaNotLike(String value) {
            addCriterion("CODLENGUA not like", value, "codlengua");
            return (Criteria) this;
        }

        public Criteria andCodlenguaIn(List<String> values) {
            addCriterion("CODLENGUA in", values, "codlengua");
            return (Criteria) this;
        }

        public Criteria andCodlenguaNotIn(List<String> values) {
            addCriterion("CODLENGUA not in", values, "codlengua");
            return (Criteria) this;
        }

        public Criteria andCodlenguaBetween(String value1, String value2) {
            addCriterion("CODLENGUA between", value1, value2, "codlengua");
            return (Criteria) this;
        }

        public Criteria andCodlenguaNotBetween(String value1, String value2) {
            addCriterion("CODLENGUA not between", value1, value2, "codlengua");
            return (Criteria) this;
        }

        public Criteria andUidActividadLikeInsensitive(String value) {
            addCriterion("upper(UID_ACTIVIDAD) like", value.toUpperCase(), "uidActividad");
            return (Criteria) this;
        }

        public Criteria andCodClienteLikeInsensitive(String value) {
            addCriterion("upper(CODCLI) like", value.toUpperCase(), "codCliente");
            return (Criteria) this;
        }

        public Criteria andDesClienteLikeInsensitive(String value) {
            addCriterion("upper(DESCLI) like", value.toUpperCase(), "desCliente");
            return (Criteria) this;
        }

        public Criteria andNombreComercialLikeInsensitive(String value) {
            addCriterion("upper(NOMBRE_COMERCIAL) like", value.toUpperCase(), "nombreComercial");
            return (Criteria) this;
        }

        public Criteria andDomicilioLikeInsensitive(String value) {
            addCriterion("upper(DOMICILIO) like", value.toUpperCase(), "domicilio");
            return (Criteria) this;
        }

        public Criteria andPoblacionLikeInsensitive(String value) {
            addCriterion("upper(POBLACION) like", value.toUpperCase(), "poblacion");
            return (Criteria) this;
        }

        public Criteria andProvinciaLikeInsensitive(String value) {
            addCriterion("upper(PROVINCIA) like", value.toUpperCase(), "provincia");
            return (Criteria) this;
        }

        public Criteria andCpLikeInsensitive(String value) {
            addCriterion("upper(CP) like", value.toUpperCase(), "cp");
            return (Criteria) this;
        }

        public Criteria andTelefono1LikeInsensitive(String value) {
            addCriterion("upper(TELEFONO1) like", value.toUpperCase(), "telefono1");
            return (Criteria) this;
        }

        public Criteria andTelefono2LikeInsensitive(String value) {
            addCriterion("upper(TELEFONO2) like", value.toUpperCase(), "telefono2");
            return (Criteria) this;
        }

        public Criteria andFaxLikeInsensitive(String value) {
            addCriterion("upper(FAX) like", value.toUpperCase(), "fax");
            return (Criteria) this;
        }

        public Criteria andCodpaisLikeInsensitive(String value) {
            addCriterion("upper(CODPAIS) like", value.toUpperCase(), "codpais");
            return (Criteria) this;
        }

        public Criteria andPersonaContactoLikeInsensitive(String value) {
            addCriterion("upper(PERSONA_CONTACTO) like", value.toUpperCase(), "personaContacto");
            return (Criteria) this;
        }

        public Criteria andEmailLikeInsensitive(String value) {
            addCriterion("upper(EMAIL) like", value.toUpperCase(), "email");
            return (Criteria) this;
        }

        public Criteria andCifLikeInsensitive(String value) {
            addCriterion("upper(CIF) like", value.toUpperCase(), "cif");
            return (Criteria) this;
        }

        public Criteria andCodtarLikeInsensitive(String value) {
            addCriterion("upper(CODTAR) like", value.toUpperCase(), "codtar");
            return (Criteria) this;
        }

        public Criteria andCodsecLikeInsensitive(String value) {
            addCriterion("upper(CODSEC) like", value.toUpperCase(), "codsec");
            return (Criteria) this;
        }

        public Criteria andBancoLikeInsensitive(String value) {
            addCriterion("upper(BANCO) like", value.toUpperCase(), "banco");
            return (Criteria) this;
        }

        public Criteria andBancoDomicilioLikeInsensitive(String value) {
            addCriterion("upper(BANCO_DOMICILIO) like", value.toUpperCase(), "bancoDomicilio");
            return (Criteria) this;
        }

        public Criteria andBancoPoblacionLikeInsensitive(String value) {
            addCriterion("upper(BANCO_POBLACION) like", value.toUpperCase(), "bancoPoblacion");
            return (Criteria) this;
        }

        public Criteria andCccLikeInsensitive(String value) {
            addCriterion("upper(CCC) like", value.toUpperCase(), "ccc");
            return (Criteria) this;
        }

        public Criteria andObservacionesLikeInsensitive(String value) {
            addCriterion("upper(OBSERVACIONES) like", value.toUpperCase(), "observaciones");
            return (Criteria) this;
        }

        public Criteria andCodcliFacturaLikeInsensitive(String value) {
            addCriterion("upper(CODCLI_FACTURA) like", value.toUpperCase(), "codcliFactura");
            return (Criteria) this;
        }

        public Criteria andLocalidadLikeInsensitive(String value) {
            addCriterion("upper(LOCALIDAD) like", value.toUpperCase(), "localidad");
            return (Criteria) this;
        }

        public Criteria andCodtipoidenLikeInsensitive(String value) {
            addCriterion("upper(CODTIPOIDEN) like", value.toUpperCase(), "codtipoiden");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}