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
package com.comerzzia.pos.persistence.core.tiendas.cajas;

import java.util.ArrayList;
import java.util.List;

public class TiendaCajaExample {
    public static final String ORDER_BY_UID_CAJA = "UID_CAJA";

    public static final String ORDER_BY_UID_CAJA_DESC = "UID_CAJA DESC";

    public static final String ORDER_BY_UID_ACTIVIDAD = "UID_ACTIVIDAD";

    public static final String ORDER_BY_UID_ACTIVIDAD_DESC = "UID_ACTIVIDAD DESC";

    public static final String ORDER_BY_CODALM = "CODALM";

    public static final String ORDER_BY_CODALM_DESC = "CODALM DESC";

    public static final String ORDER_BY_CODCAJA = "CODCAJA";

    public static final String ORDER_BY_CODCAJA_DESC = "CODCAJA DESC";

    public static final String ORDER_BY_UID_TPV = "UID_TPV";

    public static final String ORDER_BY_UID_TPV_DESC = "UID_TPV DESC";

    public static final String ORDER_BY_ACTIVO = "ACTIVO";

    public static final String ORDER_BY_ACTIVO_DESC = "ACTIVO DESC";

    public static final String ORDER_BY_CONFIGURACION = "CONFIGURACION";

    public static final String ORDER_BY_CONFIGURACION_DESC = "CONFIGURACION DESC";

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TiendaCajaExample() {
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

        public Criteria andUidCajaIsNull() {
            addCriterion("UID_CAJA is null");
            return (Criteria) this;
        }

        public Criteria andUidCajaIsNotNull() {
            addCriterion("UID_CAJA is not null");
            return (Criteria) this;
        }

        public Criteria andUidCajaEqualTo(String value) {
            addCriterion("UID_CAJA =", value, "uidCaja");
            return (Criteria) this;
        }

        public Criteria andUidCajaNotEqualTo(String value) {
            addCriterion("UID_CAJA <>", value, "uidCaja");
            return (Criteria) this;
        }

        public Criteria andUidCajaGreaterThan(String value) {
            addCriterion("UID_CAJA >", value, "uidCaja");
            return (Criteria) this;
        }

        public Criteria andUidCajaGreaterThanOrEqualTo(String value) {
            addCriterion("UID_CAJA >=", value, "uidCaja");
            return (Criteria) this;
        }

        public Criteria andUidCajaLessThan(String value) {
            addCriterion("UID_CAJA <", value, "uidCaja");
            return (Criteria) this;
        }

        public Criteria andUidCajaLessThanOrEqualTo(String value) {
            addCriterion("UID_CAJA <=", value, "uidCaja");
            return (Criteria) this;
        }

        public Criteria andUidCajaLike(String value) {
            addCriterion("UID_CAJA like", value, "uidCaja");
            return (Criteria) this;
        }

        public Criteria andUidCajaNotLike(String value) {
            addCriterion("UID_CAJA not like", value, "uidCaja");
            return (Criteria) this;
        }

        public Criteria andUidCajaIn(List<String> values) {
            addCriterion("UID_CAJA in", values, "uidCaja");
            return (Criteria) this;
        }

        public Criteria andUidCajaNotIn(List<String> values) {
            addCriterion("UID_CAJA not in", values, "uidCaja");
            return (Criteria) this;
        }

        public Criteria andUidCajaBetween(String value1, String value2) {
            addCriterion("UID_CAJA between", value1, value2, "uidCaja");
            return (Criteria) this;
        }

        public Criteria andUidCajaNotBetween(String value1, String value2) {
            addCriterion("UID_CAJA not between", value1, value2, "uidCaja");
            return (Criteria) this;
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

        public Criteria andCodAlmacenIsNull() {
            addCriterion("CODALM is null");
            return (Criteria) this;
        }

        public Criteria andCodAlmacenIsNotNull() {
            addCriterion("CODALM is not null");
            return (Criteria) this;
        }

        public Criteria andCodAlmacenEqualTo(String value) {
            addCriterion("CODALM =", value, "codAlmacen");
            return (Criteria) this;
        }

        public Criteria andCodAlmacenNotEqualTo(String value) {
            addCriterion("CODALM <>", value, "codAlmacen");
            return (Criteria) this;
        }

        public Criteria andCodAlmacenGreaterThan(String value) {
            addCriterion("CODALM >", value, "codAlmacen");
            return (Criteria) this;
        }

        public Criteria andCodAlmacenGreaterThanOrEqualTo(String value) {
            addCriterion("CODALM >=", value, "codAlmacen");
            return (Criteria) this;
        }

        public Criteria andCodAlmacenLessThan(String value) {
            addCriterion("CODALM <", value, "codAlmacen");
            return (Criteria) this;
        }

        public Criteria andCodAlmacenLessThanOrEqualTo(String value) {
            addCriterion("CODALM <=", value, "codAlmacen");
            return (Criteria) this;
        }

        public Criteria andCodAlmacenLike(String value) {
            addCriterion("CODALM like", value, "codAlmacen");
            return (Criteria) this;
        }

        public Criteria andCodAlmacenNotLike(String value) {
            addCriterion("CODALM not like", value, "codAlmacen");
            return (Criteria) this;
        }

        public Criteria andCodAlmacenIn(List<String> values) {
            addCriterion("CODALM in", values, "codAlmacen");
            return (Criteria) this;
        }

        public Criteria andCodAlmacenNotIn(List<String> values) {
            addCriterion("CODALM not in", values, "codAlmacen");
            return (Criteria) this;
        }

        public Criteria andCodAlmacenBetween(String value1, String value2) {
            addCriterion("CODALM between", value1, value2, "codAlmacen");
            return (Criteria) this;
        }

        public Criteria andCodAlmacenNotBetween(String value1, String value2) {
            addCriterion("CODALM not between", value1, value2, "codAlmacen");
            return (Criteria) this;
        }

        public Criteria andCodcajaIsNull() {
            addCriterion("CODCAJA is null");
            return (Criteria) this;
        }

        public Criteria andCodcajaIsNotNull() {
            addCriterion("CODCAJA is not null");
            return (Criteria) this;
        }

        public Criteria andCodcajaEqualTo(String value) {
            addCriterion("CODCAJA =", value, "codcaja");
            return (Criteria) this;
        }

        public Criteria andCodcajaNotEqualTo(String value) {
            addCriterion("CODCAJA <>", value, "codcaja");
            return (Criteria) this;
        }

        public Criteria andCodcajaGreaterThan(String value) {
            addCriterion("CODCAJA >", value, "codcaja");
            return (Criteria) this;
        }

        public Criteria andCodcajaGreaterThanOrEqualTo(String value) {
            addCriterion("CODCAJA >=", value, "codcaja");
            return (Criteria) this;
        }

        public Criteria andCodcajaLessThan(String value) {
            addCriterion("CODCAJA <", value, "codcaja");
            return (Criteria) this;
        }

        public Criteria andCodcajaLessThanOrEqualTo(String value) {
            addCriterion("CODCAJA <=", value, "codcaja");
            return (Criteria) this;
        }

        public Criteria andCodcajaLike(String value) {
            addCriterion("CODCAJA like", value, "codcaja");
            return (Criteria) this;
        }

        public Criteria andCodcajaNotLike(String value) {
            addCriterion("CODCAJA not like", value, "codcaja");
            return (Criteria) this;
        }

        public Criteria andCodcajaIn(List<String> values) {
            addCriterion("CODCAJA in", values, "codcaja");
            return (Criteria) this;
        }

        public Criteria andCodcajaNotIn(List<String> values) {
            addCriterion("CODCAJA not in", values, "codcaja");
            return (Criteria) this;
        }

        public Criteria andCodcajaBetween(String value1, String value2) {
            addCriterion("CODCAJA between", value1, value2, "codcaja");
            return (Criteria) this;
        }

        public Criteria andCodcajaNotBetween(String value1, String value2) {
            addCriterion("CODCAJA not between", value1, value2, "codcaja");
            return (Criteria) this;
        }

        public Criteria andUidTpvIsNull() {
            addCriterion("UID_TPV is null");
            return (Criteria) this;
        }

        public Criteria andUidTpvIsNotNull() {
            addCriterion("UID_TPV is not null");
            return (Criteria) this;
        }

        public Criteria andUidTpvEqualTo(String value) {
            addCriterion("UID_TPV =", value, "uidTpv");
            return (Criteria) this;
        }

        public Criteria andUidTpvNotEqualTo(String value) {
            addCriterion("UID_TPV <>", value, "uidTpv");
            return (Criteria) this;
        }

        public Criteria andUidTpvGreaterThan(String value) {
            addCriterion("UID_TPV >", value, "uidTpv");
            return (Criteria) this;
        }

        public Criteria andUidTpvGreaterThanOrEqualTo(String value) {
            addCriterion("UID_TPV >=", value, "uidTpv");
            return (Criteria) this;
        }

        public Criteria andUidTpvLessThan(String value) {
            addCriterion("UID_TPV <", value, "uidTpv");
            return (Criteria) this;
        }

        public Criteria andUidTpvLessThanOrEqualTo(String value) {
            addCriterion("UID_TPV <=", value, "uidTpv");
            return (Criteria) this;
        }

        public Criteria andUidTpvLike(String value) {
            addCriterion("UID_TPV like", value, "uidTpv");
            return (Criteria) this;
        }

        public Criteria andUidTpvNotLike(String value) {
            addCriterion("UID_TPV not like", value, "uidTpv");
            return (Criteria) this;
        }

        public Criteria andUidTpvIn(List<String> values) {
            addCriterion("UID_TPV in", values, "uidTpv");
            return (Criteria) this;
        }

        public Criteria andUidTpvNotIn(List<String> values) {
            addCriterion("UID_TPV not in", values, "uidTpv");
            return (Criteria) this;
        }

        public Criteria andUidTpvBetween(String value1, String value2) {
            addCriterion("UID_TPV between", value1, value2, "uidTpv");
            return (Criteria) this;
        }

        public Criteria andUidTpvNotBetween(String value1, String value2) {
            addCriterion("UID_TPV not between", value1, value2, "uidTpv");
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

        public Criteria andUidCajaLikeInsensitive(String value) {
            addCriterion("upper(UID_CAJA) like", value.toUpperCase(), "uidCaja");
            return (Criteria) this;
        }

        public Criteria andUidActividadLikeInsensitive(String value) {
            addCriterion("upper(UID_ACTIVIDAD) like", value.toUpperCase(), "uidActividad");
            return (Criteria) this;
        }

        public Criteria andCodAlmacenLikeInsensitive(String value) {
            addCriterion("upper(CODALM) like", value.toUpperCase(), "codAlmacen");
            return (Criteria) this;
        }

        public Criteria andCodcajaLikeInsensitive(String value) {
            addCriterion("upper(CODCAJA) like", value.toUpperCase(), "codcaja");
            return (Criteria) this;
        }

        public Criteria andUidTpvLikeInsensitive(String value) {
            addCriterion("upper(UID_TPV) like", value.toUpperCase(), "uidTpv");
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