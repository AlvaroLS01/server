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
package com.comerzzia.pos.persistence.core.documentos.tipos;

import java.util.ArrayList;
import java.util.List;

public class TipoDocumentoExample {
    public static final String ORDER_BY_UID_ACTIVIDAD = "UID_ACTIVIDAD";

    public static final String ORDER_BY_UID_ACTIVIDAD_DESC = "UID_ACTIVIDAD DESC";

    public static final String ORDER_BY_ID_TIPO_DOCUMENTO = "ID_TIPO_DOCUMENTO";

    public static final String ORDER_BY_ID_TIPO_DOCUMENTO_DESC = "ID_TIPO_DOCUMENTO DESC";

    public static final String ORDER_BY_DESTIPODOCUMENTO = "DESTIPODOCUMENTO";

    public static final String ORDER_BY_DESTIPODOCUMENTO_DESC = "DESTIPODOCUMENTO DESC";

    public static final String ORDER_BY_ID_CONTADOR = "ID_CONTADOR";

    public static final String ORDER_BY_ID_CONTADOR_DESC = "ID_CONTADOR DESC";

    public static final String ORDER_BY_CODTIPODOCUMENTO = "CODTIPODOCUMENTO";

    public static final String ORDER_BY_CODTIPODOCUMENTO_DESC = "CODTIPODOCUMENTO DESC";

    public static final String ORDER_BY_CODPAIS = "CODPAIS";

    public static final String ORDER_BY_CODPAIS_DESC = "CODPAIS DESC";

    public static final String ORDER_BY_CLASE_PROCESAMIENTO = "CLASE_PROCESAMIENTO";

    public static final String ORDER_BY_CLASE_PROCESAMIENTO_DESC = "CLASE_PROCESAMIENTO DESC";

    public static final String ORDER_BY_CODAPLICACION = "CODAPLICACION";

    public static final String ORDER_BY_CODAPLICACION_DESC = "CODAPLICACION DESC";

    public static final String ORDER_BY_CODCONALM = "CODCONALM";

    public static final String ORDER_BY_CODCONALM_DESC = "CODCONALM DESC";

    public static final String ORDER_BY_AMBITO_APLICACION = "AMBITO_APLICACION";

    public static final String ORDER_BY_AMBITO_APLICACION_DESC = "AMBITO_APLICACION DESC";

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TipoDocumentoExample() {
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
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition != null) {
                criteria.add(new Criterion(condition));
            }
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value != null) {
                criteria.add(new Criterion(condition, value));
            }
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 != null && value2 != null) {
                criteria.add(new Criterion(condition, value1, value2));
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

        public Criteria andIdTipoDocumentoIsNull() {
            addCriterion("ID_TIPO_DOCUMENTO is null");
            return (Criteria) this;
        }

        public Criteria andIdTipoDocumentoIsNotNull() {
            addCriterion("ID_TIPO_DOCUMENTO is not null");
            return (Criteria) this;
        }

        public Criteria andIdTipoDocumentoEqualTo(Long value) {
            addCriterion("ID_TIPO_DOCUMENTO =", value, "idTipoDocumento");
            return (Criteria) this;
        }

        public Criteria andIdTipoDocumentoNotEqualTo(Long value) {
            addCriterion("ID_TIPO_DOCUMENTO <>", value, "idTipoDocumento");
            return (Criteria) this;
        }

        public Criteria andIdTipoDocumentoGreaterThan(Long value) {
            addCriterion("ID_TIPO_DOCUMENTO >", value, "idTipoDocumento");
            return (Criteria) this;
        }

        public Criteria andIdTipoDocumentoGreaterThanOrEqualTo(Long value) {
            addCriterion("ID_TIPO_DOCUMENTO >=", value, "idTipoDocumento");
            return (Criteria) this;
        }

        public Criteria andIdTipoDocumentoLessThan(Long value) {
            addCriterion("ID_TIPO_DOCUMENTO <", value, "idTipoDocumento");
            return (Criteria) this;
        }

        public Criteria andIdTipoDocumentoLessThanOrEqualTo(Long value) {
            addCriterion("ID_TIPO_DOCUMENTO <=", value, "idTipoDocumento");
            return (Criteria) this;
        }

        public Criteria andIdTipoDocumentoIn(List<Long> values) {
            addCriterion("ID_TIPO_DOCUMENTO in", values, "idTipoDocumento");
            return (Criteria) this;
        }

        public Criteria andIdTipoDocumentoNotIn(List<Long> values) {
            addCriterion("ID_TIPO_DOCUMENTO not in", values, "idTipoDocumento");
            return (Criteria) this;
        }

        public Criteria andIdTipoDocumentoBetween(Long value1, Long value2) {
            addCriterion("ID_TIPO_DOCUMENTO between", value1, value2, "idTipoDocumento");
            return (Criteria) this;
        }

        public Criteria andIdTipoDocumentoNotBetween(Long value1, Long value2) {
            addCriterion("ID_TIPO_DOCUMENTO not between", value1, value2, "idTipoDocumento");
            return (Criteria) this;
        }

        public Criteria andDestipodocumentoIsNull() {
            addCriterion("DESTIPODOCUMENTO is null");
            return (Criteria) this;
        }

        public Criteria andDestipodocumentoIsNotNull() {
            addCriterion("DESTIPODOCUMENTO is not null");
            return (Criteria) this;
        }

        public Criteria andDestipodocumentoEqualTo(String value) {
            addCriterion("DESTIPODOCUMENTO =", value, "destipodocumento");
            return (Criteria) this;
        }

        public Criteria andDestipodocumentoNotEqualTo(String value) {
            addCriterion("DESTIPODOCUMENTO <>", value, "destipodocumento");
            return (Criteria) this;
        }

        public Criteria andDestipodocumentoGreaterThan(String value) {
            addCriterion("DESTIPODOCUMENTO >", value, "destipodocumento");
            return (Criteria) this;
        }

        public Criteria andDestipodocumentoGreaterThanOrEqualTo(String value) {
            addCriterion("DESTIPODOCUMENTO >=", value, "destipodocumento");
            return (Criteria) this;
        }

        public Criteria andDestipodocumentoLessThan(String value) {
            addCriterion("DESTIPODOCUMENTO <", value, "destipodocumento");
            return (Criteria) this;
        }

        public Criteria andDestipodocumentoLessThanOrEqualTo(String value) {
            addCriterion("DESTIPODOCUMENTO <=", value, "destipodocumento");
            return (Criteria) this;
        }

        public Criteria andDestipodocumentoLike(String value) {
            addCriterion("DESTIPODOCUMENTO like", value, "destipodocumento");
            return (Criteria) this;
        }

        public Criteria andDestipodocumentoNotLike(String value) {
            addCriterion("DESTIPODOCUMENTO not like", value, "destipodocumento");
            return (Criteria) this;
        }

        public Criteria andDestipodocumentoIn(List<String> values) {
            addCriterion("DESTIPODOCUMENTO in", values, "destipodocumento");
            return (Criteria) this;
        }

        public Criteria andDestipodocumentoNotIn(List<String> values) {
            addCriterion("DESTIPODOCUMENTO not in", values, "destipodocumento");
            return (Criteria) this;
        }

        public Criteria andDestipodocumentoBetween(String value1, String value2) {
            addCriterion("DESTIPODOCUMENTO between", value1, value2, "destipodocumento");
            return (Criteria) this;
        }

        public Criteria andDestipodocumentoNotBetween(String value1, String value2) {
            addCriterion("DESTIPODOCUMENTO not between", value1, value2, "destipodocumento");
            return (Criteria) this;
        }

        public Criteria andIdContadorIsNull() {
            addCriterion("ID_CONTADOR is null");
            return (Criteria) this;
        }

        public Criteria andIdContadorIsNotNull() {
            addCriterion("ID_CONTADOR is not null");
            return (Criteria) this;
        }

        public Criteria andIdContadorEqualTo(String value) {
            addCriterion("ID_CONTADOR =", value, "idContador");
            return (Criteria) this;
        }

        public Criteria andIdContadorNotEqualTo(String value) {
            addCriterion("ID_CONTADOR <>", value, "idContador");
            return (Criteria) this;
        }

        public Criteria andIdContadorGreaterThan(String value) {
            addCriterion("ID_CONTADOR >", value, "idContador");
            return (Criteria) this;
        }

        public Criteria andIdContadorGreaterThanOrEqualTo(String value) {
            addCriterion("ID_CONTADOR >=", value, "idContador");
            return (Criteria) this;
        }

        public Criteria andIdContadorLessThan(String value) {
            addCriterion("ID_CONTADOR <", value, "idContador");
            return (Criteria) this;
        }

        public Criteria andIdContadorLessThanOrEqualTo(String value) {
            addCriterion("ID_CONTADOR <=", value, "idContador");
            return (Criteria) this;
        }

        public Criteria andIdContadorLike(String value) {
            addCriterion("ID_CONTADOR like", value, "idContador");
            return (Criteria) this;
        }

        public Criteria andIdContadorNotLike(String value) {
            addCriterion("ID_CONTADOR not like", value, "idContador");
            return (Criteria) this;
        }

        public Criteria andIdContadorIn(List<String> values) {
            addCriterion("ID_CONTADOR in", values, "idContador");
            return (Criteria) this;
        }

        public Criteria andIdContadorNotIn(List<String> values) {
            addCriterion("ID_CONTADOR not in", values, "idContador");
            return (Criteria) this;
        }

        public Criteria andIdContadorBetween(String value1, String value2) {
            addCriterion("ID_CONTADOR between", value1, value2, "idContador");
            return (Criteria) this;
        }

        public Criteria andIdContadorNotBetween(String value1, String value2) {
            addCriterion("ID_CONTADOR not between", value1, value2, "idContador");
            return (Criteria) this;
        }

        public Criteria andCodtipodocumentoIsNull() {
            addCriterion("CODTIPODOCUMENTO is null");
            return (Criteria) this;
        }

        public Criteria andCodtipodocumentoIsNotNull() {
            addCriterion("CODTIPODOCUMENTO is not null");
            return (Criteria) this;
        }

        public Criteria andCodtipodocumentoEqualTo(String value) {
            addCriterion("CODTIPODOCUMENTO =", value, "codtipodocumento");
            return (Criteria) this;
        }

        public Criteria andCodtipodocumentoNotEqualTo(String value) {
            addCriterion("CODTIPODOCUMENTO <>", value, "codtipodocumento");
            return (Criteria) this;
        }

        public Criteria andCodtipodocumentoGreaterThan(String value) {
            addCriterion("CODTIPODOCUMENTO >", value, "codtipodocumento");
            return (Criteria) this;
        }

        public Criteria andCodtipodocumentoGreaterThanOrEqualTo(String value) {
            addCriterion("CODTIPODOCUMENTO >=", value, "codtipodocumento");
            return (Criteria) this;
        }

        public Criteria andCodtipodocumentoLessThan(String value) {
            addCriterion("CODTIPODOCUMENTO <", value, "codtipodocumento");
            return (Criteria) this;
        }

        public Criteria andCodtipodocumentoLessThanOrEqualTo(String value) {
            addCriterion("CODTIPODOCUMENTO <=", value, "codtipodocumento");
            return (Criteria) this;
        }

        public Criteria andCodtipodocumentoLike(String value) {
            addCriterion("CODTIPODOCUMENTO like", value, "codtipodocumento");
            return (Criteria) this;
        }

        public Criteria andCodtipodocumentoNotLike(String value) {
            addCriterion("CODTIPODOCUMENTO not like", value, "codtipodocumento");
            return (Criteria) this;
        }

        public Criteria andCodtipodocumentoIn(List<String> values) {
            addCriterion("CODTIPODOCUMENTO in", values, "codtipodocumento");
            return (Criteria) this;
        }

        public Criteria andCodtipodocumentoNotIn(List<String> values) {
            addCriterion("CODTIPODOCUMENTO not in", values, "codtipodocumento");
            return (Criteria) this;
        }

        public Criteria andCodtipodocumentoBetween(String value1, String value2) {
            addCriterion("CODTIPODOCUMENTO between", value1, value2, "codtipodocumento");
            return (Criteria) this;
        }

        public Criteria andCodtipodocumentoNotBetween(String value1, String value2) {
            addCriterion("CODTIPODOCUMENTO not between", value1, value2, "codtipodocumento");
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

        public Criteria andClaseProcesamientoIsNull() {
            addCriterion("CLASE_PROCESAMIENTO is null");
            return (Criteria) this;
        }

        public Criteria andClaseProcesamientoIsNotNull() {
            addCriterion("CLASE_PROCESAMIENTO is not null");
            return (Criteria) this;
        }

        public Criteria andClaseProcesamientoEqualTo(String value) {
            addCriterion("CLASE_PROCESAMIENTO =", value, "claseProcesamiento");
            return (Criteria) this;
        }

        public Criteria andClaseProcesamientoNotEqualTo(String value) {
            addCriterion("CLASE_PROCESAMIENTO <>", value, "claseProcesamiento");
            return (Criteria) this;
        }

        public Criteria andClaseProcesamientoGreaterThan(String value) {
            addCriterion("CLASE_PROCESAMIENTO >", value, "claseProcesamiento");
            return (Criteria) this;
        }

        public Criteria andClaseProcesamientoGreaterThanOrEqualTo(String value) {
            addCriterion("CLASE_PROCESAMIENTO >=", value, "claseProcesamiento");
            return (Criteria) this;
        }

        public Criteria andClaseProcesamientoLessThan(String value) {
            addCriterion("CLASE_PROCESAMIENTO <", value, "claseProcesamiento");
            return (Criteria) this;
        }

        public Criteria andClaseProcesamientoLessThanOrEqualTo(String value) {
            addCriterion("CLASE_PROCESAMIENTO <=", value, "claseProcesamiento");
            return (Criteria) this;
        }

        public Criteria andClaseProcesamientoLike(String value) {
            addCriterion("CLASE_PROCESAMIENTO like", value, "claseProcesamiento");
            return (Criteria) this;
        }

        public Criteria andClaseProcesamientoNotLike(String value) {
            addCriterion("CLASE_PROCESAMIENTO not like", value, "claseProcesamiento");
            return (Criteria) this;
        }

        public Criteria andClaseProcesamientoIn(List<String> values) {
            addCriterion("CLASE_PROCESAMIENTO in", values, "claseProcesamiento");
            return (Criteria) this;
        }

        public Criteria andClaseProcesamientoNotIn(List<String> values) {
            addCriterion("CLASE_PROCESAMIENTO not in", values, "claseProcesamiento");
            return (Criteria) this;
        }

        public Criteria andClaseProcesamientoBetween(String value1, String value2) {
            addCriterion("CLASE_PROCESAMIENTO between", value1, value2, "claseProcesamiento");
            return (Criteria) this;
        }

        public Criteria andClaseProcesamientoNotBetween(String value1, String value2) {
            addCriterion("CLASE_PROCESAMIENTO not between", value1, value2, "claseProcesamiento");
            return (Criteria) this;
        }

        public Criteria andCodaplicacionIsNull() {
            addCriterion("CODAPLICACION is null");
            return (Criteria) this;
        }

        public Criteria andCodaplicacionIsNotNull() {
            addCriterion("CODAPLICACION is not null");
            return (Criteria) this;
        }

        public Criteria andCodaplicacionEqualTo(String value) {
            addCriterion("CODAPLICACION =", value, "codaplicacion");
            return (Criteria) this;
        }

        public Criteria andCodaplicacionNotEqualTo(String value) {
            addCriterion("CODAPLICACION <>", value, "codaplicacion");
            return (Criteria) this;
        }

        public Criteria andCodaplicacionGreaterThan(String value) {
            addCriterion("CODAPLICACION >", value, "codaplicacion");
            return (Criteria) this;
        }

        public Criteria andCodaplicacionGreaterThanOrEqualTo(String value) {
            addCriterion("CODAPLICACION >=", value, "codaplicacion");
            return (Criteria) this;
        }

        public Criteria andCodaplicacionLessThan(String value) {
            addCriterion("CODAPLICACION <", value, "codaplicacion");
            return (Criteria) this;
        }

        public Criteria andCodaplicacionLessThanOrEqualTo(String value) {
            addCriterion("CODAPLICACION <=", value, "codaplicacion");
            return (Criteria) this;
        }

        public Criteria andCodaplicacionLike(String value) {
            addCriterion("CODAPLICACION like", value, "codaplicacion");
            return (Criteria) this;
        }

        public Criteria andCodaplicacionNotLike(String value) {
            addCriterion("CODAPLICACION not like", value, "codaplicacion");
            return (Criteria) this;
        }

        public Criteria andCodaplicacionIn(List<String> values) {
            addCriterion("CODAPLICACION in", values, "codaplicacion");
            return (Criteria) this;
        }

        public Criteria andCodaplicacionNotIn(List<String> values) {
            addCriterion("CODAPLICACION not in", values, "codaplicacion");
            return (Criteria) this;
        }

        public Criteria andCodaplicacionBetween(String value1, String value2) {
            addCriterion("CODAPLICACION between", value1, value2, "codaplicacion");
            return (Criteria) this;
        }

        public Criteria andCodaplicacionNotBetween(String value1, String value2) {
            addCriterion("CODAPLICACION not between", value1, value2, "codaplicacion");
            return (Criteria) this;
        }

        public Criteria andCodconalmIsNull() {
            addCriterion("CODCONALM is null");
            return (Criteria) this;
        }

        public Criteria andCodconalmIsNotNull() {
            addCriterion("CODCONALM is not null");
            return (Criteria) this;
        }

        public Criteria andCodconalmEqualTo(String value) {
            addCriterion("CODCONALM =", value, "codconalm");
            return (Criteria) this;
        }

        public Criteria andCodconalmNotEqualTo(String value) {
            addCriterion("CODCONALM <>", value, "codconalm");
            return (Criteria) this;
        }

        public Criteria andCodconalmGreaterThan(String value) {
            addCriterion("CODCONALM >", value, "codconalm");
            return (Criteria) this;
        }

        public Criteria andCodconalmGreaterThanOrEqualTo(String value) {
            addCriterion("CODCONALM >=", value, "codconalm");
            return (Criteria) this;
        }

        public Criteria andCodconalmLessThan(String value) {
            addCriterion("CODCONALM <", value, "codconalm");
            return (Criteria) this;
        }

        public Criteria andCodconalmLessThanOrEqualTo(String value) {
            addCriterion("CODCONALM <=", value, "codconalm");
            return (Criteria) this;
        }

        public Criteria andCodconalmLike(String value) {
            addCriterion("CODCONALM like", value, "codconalm");
            return (Criteria) this;
        }

        public Criteria andCodconalmNotLike(String value) {
            addCriterion("CODCONALM not like", value, "codconalm");
            return (Criteria) this;
        }

        public Criteria andCodconalmIn(List<String> values) {
            addCriterion("CODCONALM in", values, "codconalm");
            return (Criteria) this;
        }

        public Criteria andCodconalmNotIn(List<String> values) {
            addCriterion("CODCONALM not in", values, "codconalm");
            return (Criteria) this;
        }

        public Criteria andCodconalmBetween(String value1, String value2) {
            addCriterion("CODCONALM between", value1, value2, "codconalm");
            return (Criteria) this;
        }

        public Criteria andCodconalmNotBetween(String value1, String value2) {
            addCriterion("CODCONALM not between", value1, value2, "codconalm");
            return (Criteria) this;
        }

        public Criteria andAmbitoAplicacionIsNull() {
            addCriterion("AMBITO_APLICACION is null");
            return (Criteria) this;
        }

        public Criteria andAmbitoAplicacionIsNotNull() {
            addCriterion("AMBITO_APLICACION is not null");
            return (Criteria) this;
        }

        public Criteria andAmbitoAplicacionEqualTo(String value) {
            addCriterion("AMBITO_APLICACION =", value, "ambitoAplicacion");
            return (Criteria) this;
        }

        public Criteria andAmbitoAplicacionNotEqualTo(String value) {
            addCriterion("AMBITO_APLICACION <>", value, "ambitoAplicacion");
            return (Criteria) this;
        }

        public Criteria andAmbitoAplicacionGreaterThan(String value) {
            addCriterion("AMBITO_APLICACION >", value, "ambitoAplicacion");
            return (Criteria) this;
        }

        public Criteria andAmbitoAplicacionGreaterThanOrEqualTo(String value) {
            addCriterion("AMBITO_APLICACION >=", value, "ambitoAplicacion");
            return (Criteria) this;
        }

        public Criteria andAmbitoAplicacionLessThan(String value) {
            addCriterion("AMBITO_APLICACION <", value, "ambitoAplicacion");
            return (Criteria) this;
        }

        public Criteria andAmbitoAplicacionLessThanOrEqualTo(String value) {
            addCriterion("AMBITO_APLICACION <=", value, "ambitoAplicacion");
            return (Criteria) this;
        }

        public Criteria andAmbitoAplicacionLike(String value) {
            addCriterion("AMBITO_APLICACION like", value, "ambitoAplicacion");
            return (Criteria) this;
        }

        public Criteria andAmbitoAplicacionNotLike(String value) {
            addCriterion("AMBITO_APLICACION not like", value, "ambitoAplicacion");
            return (Criteria) this;
        }

        public Criteria andAmbitoAplicacionIn(List<String> values) {
            addCriterion("AMBITO_APLICACION in", values, "ambitoAplicacion");
            return (Criteria) this;
        }

        public Criteria andAmbitoAplicacionNotIn(List<String> values) {
            addCriterion("AMBITO_APLICACION not in", values, "ambitoAplicacion");
            return (Criteria) this;
        }

        public Criteria andAmbitoAplicacionBetween(String value1, String value2) {
            addCriterion("AMBITO_APLICACION between", value1, value2, "ambitoAplicacion");
            return (Criteria) this;
        }

        public Criteria andAmbitoAplicacionNotBetween(String value1, String value2) {
            addCriterion("AMBITO_APLICACION not between", value1, value2, "ambitoAplicacion");
            return (Criteria) this;
        }

        public Criteria andUidActividadLikeInsensitive(String value) {
            addCriterion("upper(UID_ACTIVIDAD) like", value.toUpperCase(), "uidActividad");
            return (Criteria) this;
        }

        public Criteria andDestipodocumentoLikeInsensitive(String value) {
            addCriterion("upper(DESTIPODOCUMENTO) like", value.toUpperCase(), "destipodocumento");
            return (Criteria) this;
        }

        public Criteria andIdContadorLikeInsensitive(String value) {
            addCriterion("upper(ID_CONTADOR) like", value.toUpperCase(), "idContador");
            return (Criteria) this;
        }

        public Criteria andCodtipodocumentoLikeInsensitive(String value) {
            addCriterion("upper(CODTIPODOCUMENTO) like", value.toUpperCase(), "codtipodocumento");
            return (Criteria) this;
        }

        public Criteria andCodpaisLikeInsensitive(String value) {
            addCriterion("upper(CODPAIS) like", value.toUpperCase(), "codpais");
            return (Criteria) this;
        }

        public Criteria andClaseProcesamientoLikeInsensitive(String value) {
            addCriterion("upper(CLASE_PROCESAMIENTO) like", value.toUpperCase(), "claseProcesamiento");
            return (Criteria) this;
        }

        public Criteria andCodaplicacionLikeInsensitive(String value) {
            addCriterion("upper(CODAPLICACION) like", value.toUpperCase(), "codaplicacion");
            return (Criteria) this;
        }

        public Criteria andCodconalmLikeInsensitive(String value) {
            addCriterion("upper(CODCONALM) like", value.toUpperCase(), "codconalm");
            return (Criteria) this;
        }

        public Criteria andAmbitoAplicacionLikeInsensitive(String value) {
            addCriterion("upper(AMBITO_APLICACION) like", value.toUpperCase(), "ambitoAplicacion");
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