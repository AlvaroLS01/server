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
package com.comerzzia.pos.persistence.core.empresas;

import java.util.ArrayList;
import java.util.List;

public class EmpresaExample {
    public static final String ORDER_BY_UID_ACTIVIDAD = "UID_ACTIVIDAD";

    public static final String ORDER_BY_UID_ACTIVIDAD_DESC = "UID_ACTIVIDAD DESC";

    public static final String ORDER_BY_CODEMP = "CODEMP";

    public static final String ORDER_BY_CODEMP_DESC = "CODEMP DESC";

    public static final String ORDER_BY_DESEMP = "DESEMP";

    public static final String ORDER_BY_DESEMP_DESC = "DESEMP DESC";

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

    public static final String ORDER_BY_CIF = "CIF";

    public static final String ORDER_BY_CIF_DESC = "CIF DESC";

    public static final String ORDER_BY_TELEFONO1 = "TELEFONO1";

    public static final String ORDER_BY_TELEFONO1_DESC = "TELEFONO1 DESC";

    public static final String ORDER_BY_TELEFONO2 = "TELEFONO2";

    public static final String ORDER_BY_TELEFONO2_DESC = "TELEFONO2 DESC";

    public static final String ORDER_BY_FAX = "FAX";

    public static final String ORDER_BY_FAX_DESC = "FAX DESC";

    public static final String ORDER_BY_ACTIVO = "ACTIVO";

    public static final String ORDER_BY_ACTIVO_DESC = "ACTIVO DESC";

    public static final String ORDER_BY_REGISTRO_MERCANTIL = "REGISTRO_MERCANTIL";

    public static final String ORDER_BY_REGISTRO_MERCANTIL_DESC = "REGISTRO_MERCANTIL DESC";

    public static final String ORDER_BY_LOGOTIPO = "LOGOTIPO";

    public static final String ORDER_BY_LOGOTIPO_DESC = "LOGOTIPO DESC";

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EmpresaExample() {
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

        public Criteria andCodEmpresaIsNull() {
            addCriterion("CODEMP is null");
            return (Criteria) this;
        }

        public Criteria andCodEmpresaIsNotNull() {
            addCriterion("CODEMP is not null");
            return (Criteria) this;
        }

        public Criteria andCodEmpresaEqualTo(String value) {
            addCriterion("CODEMP =", value, "codEmpresa");
            return (Criteria) this;
        }

        public Criteria andCodEmpresaNotEqualTo(String value) {
            addCriterion("CODEMP <>", value, "codEmpresa");
            return (Criteria) this;
        }

        public Criteria andCodEmpresaGreaterThan(String value) {
            addCriterion("CODEMP >", value, "codEmpresa");
            return (Criteria) this;
        }

        public Criteria andCodEmpresaGreaterThanOrEqualTo(String value) {
            addCriterion("CODEMP >=", value, "codEmpresa");
            return (Criteria) this;
        }

        public Criteria andCodEmpresaLessThan(String value) {
            addCriterion("CODEMP <", value, "codEmpresa");
            return (Criteria) this;
        }

        public Criteria andCodEmpresaLessThanOrEqualTo(String value) {
            addCriterion("CODEMP <=", value, "codEmpresa");
            return (Criteria) this;
        }

        public Criteria andCodEmpresaLike(String value) {
            addCriterion("CODEMP like", value, "codEmpresa");
            return (Criteria) this;
        }

        public Criteria andCodEmpresaNotLike(String value) {
            addCriterion("CODEMP not like", value, "codEmpresa");
            return (Criteria) this;
        }

        public Criteria andCodEmpresaIn(List<String> values) {
            addCriterion("CODEMP in", values, "codEmpresa");
            return (Criteria) this;
        }

        public Criteria andCodEmpresaNotIn(List<String> values) {
            addCriterion("CODEMP not in", values, "codEmpresa");
            return (Criteria) this;
        }

        public Criteria andCodEmpresaBetween(String value1, String value2) {
            addCriterion("CODEMP between", value1, value2, "codEmpresa");
            return (Criteria) this;
        }

        public Criteria andCodEmpresaNotBetween(String value1, String value2) {
            addCriterion("CODEMP not between", value1, value2, "codEmpresa");
            return (Criteria) this;
        }

        public Criteria andDesEmpresaIsNull() {
            addCriterion("DESEMP is null");
            return (Criteria) this;
        }

        public Criteria andDesEmpresaIsNotNull() {
            addCriterion("DESEMP is not null");
            return (Criteria) this;
        }

        public Criteria andDesEmpresaEqualTo(String value) {
            addCriterion("DESEMP =", value, "desEmpresa");
            return (Criteria) this;
        }

        public Criteria andDesEmpresaNotEqualTo(String value) {
            addCriterion("DESEMP <>", value, "desEmpresa");
            return (Criteria) this;
        }

        public Criteria andDesEmpresaGreaterThan(String value) {
            addCriterion("DESEMP >", value, "desEmpresa");
            return (Criteria) this;
        }

        public Criteria andDesEmpresaGreaterThanOrEqualTo(String value) {
            addCriterion("DESEMP >=", value, "desEmpresa");
            return (Criteria) this;
        }

        public Criteria andDesEmpresaLessThan(String value) {
            addCriterion("DESEMP <", value, "desEmpresa");
            return (Criteria) this;
        }

        public Criteria andDesEmpresaLessThanOrEqualTo(String value) {
            addCriterion("DESEMP <=", value, "desEmpresa");
            return (Criteria) this;
        }

        public Criteria andDesEmpresaLike(String value) {
            addCriterion("DESEMP like", value, "desEmpresa");
            return (Criteria) this;
        }

        public Criteria andDesEmpresaNotLike(String value) {
            addCriterion("DESEMP not like", value, "desEmpresa");
            return (Criteria) this;
        }

        public Criteria andDesEmpresaIn(List<String> values) {
            addCriterion("DESEMP in", values, "desEmpresa");
            return (Criteria) this;
        }

        public Criteria andDesEmpresaNotIn(List<String> values) {
            addCriterion("DESEMP not in", values, "desEmpresa");
            return (Criteria) this;
        }

        public Criteria andDesEmpresaBetween(String value1, String value2) {
            addCriterion("DESEMP between", value1, value2, "desEmpresa");
            return (Criteria) this;
        }

        public Criteria andDesEmpresaNotBetween(String value1, String value2) {
            addCriterion("DESEMP not between", value1, value2, "desEmpresa");
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

        public Criteria andRegistroMercantilIsNull() {
            addCriterion("REGISTRO_MERCANTIL is null");
            return (Criteria) this;
        }

        public Criteria andRegistroMercantilIsNotNull() {
            addCriterion("REGISTRO_MERCANTIL is not null");
            return (Criteria) this;
        }

        public Criteria andRegistroMercantilEqualTo(String value) {
            addCriterion("REGISTRO_MERCANTIL =", value, "registroMercantil");
            return (Criteria) this;
        }

        public Criteria andRegistroMercantilNotEqualTo(String value) {
            addCriterion("REGISTRO_MERCANTIL <>", value, "registroMercantil");
            return (Criteria) this;
        }

        public Criteria andRegistroMercantilGreaterThan(String value) {
            addCriterion("REGISTRO_MERCANTIL >", value, "registroMercantil");
            return (Criteria) this;
        }

        public Criteria andRegistroMercantilGreaterThanOrEqualTo(String value) {
            addCriterion("REGISTRO_MERCANTIL >=", value, "registroMercantil");
            return (Criteria) this;
        }

        public Criteria andRegistroMercantilLessThan(String value) {
            addCriterion("REGISTRO_MERCANTIL <", value, "registroMercantil");
            return (Criteria) this;
        }

        public Criteria andRegistroMercantilLessThanOrEqualTo(String value) {
            addCriterion("REGISTRO_MERCANTIL <=", value, "registroMercantil");
            return (Criteria) this;
        }

        public Criteria andRegistroMercantilLike(String value) {
            addCriterion("REGISTRO_MERCANTIL like", value, "registroMercantil");
            return (Criteria) this;
        }

        public Criteria andRegistroMercantilNotLike(String value) {
            addCriterion("REGISTRO_MERCANTIL not like", value, "registroMercantil");
            return (Criteria) this;
        }

        public Criteria andRegistroMercantilIn(List<String> values) {
            addCriterion("REGISTRO_MERCANTIL in", values, "registroMercantil");
            return (Criteria) this;
        }

        public Criteria andRegistroMercantilNotIn(List<String> values) {
            addCriterion("REGISTRO_MERCANTIL not in", values, "registroMercantil");
            return (Criteria) this;
        }

        public Criteria andRegistroMercantilBetween(String value1, String value2) {
            addCriterion("REGISTRO_MERCANTIL between", value1, value2, "registroMercantil");
            return (Criteria) this;
        }

        public Criteria andRegistroMercantilNotBetween(String value1, String value2) {
            addCriterion("REGISTRO_MERCANTIL not between", value1, value2, "registroMercantil");
            return (Criteria) this;
        }

        public Criteria andUidActividadLikeInsensitive(String value) {
            addCriterion("upper(UID_ACTIVIDAD) like", value.toUpperCase(), "uidActividad");
            return (Criteria) this;
        }

        public Criteria andCodEmpresaLikeInsensitive(String value) {
            addCriterion("upper(CODEMP) like", value.toUpperCase(), "codEmpresa");
            return (Criteria) this;
        }

        public Criteria andDesEmpresaLikeInsensitive(String value) {
            addCriterion("upper(DESEMP) like", value.toUpperCase(), "desEmpresa");
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

        public Criteria andCifLikeInsensitive(String value) {
            addCriterion("upper(CIF) like", value.toUpperCase(), "cif");
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

        public Criteria andRegistroMercantilLikeInsensitive(String value) {
            addCriterion("upper(REGISTRO_MERCANTIL) like", value.toUpperCase(), "registroMercantil");
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