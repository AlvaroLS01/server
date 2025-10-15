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
package com.comerzzia.pos.persistence.core.conceptosalmacen;

import java.util.ArrayList;
import java.util.List;

public class ConceptoAlmacenExample {
    public static final String ORDER_BY_UID_ACTIVIDAD = "UID_ACTIVIDAD";

    public static final String ORDER_BY_UID_ACTIVIDAD_DESC = "UID_ACTIVIDAD DESC";

    public static final String ORDER_BY_CODAPLICACION = "CODAPLICACION";

    public static final String ORDER_BY_CODAPLICACION_DESC = "CODAPLICACION DESC";

    public static final String ORDER_BY_CODCONALM = "CODCONALM";

    public static final String ORDER_BY_CODCONALM_DESC = "CODCONALM DESC";

    public static final String ORDER_BY_DESCONALM = "DESCONALM";

    public static final String ORDER_BY_DESCONALM_DESC = "DESCONALM DESC";

    public static final String ORDER_BY_SIGNO = "SIGNO";

    public static final String ORDER_BY_SIGNO_DESC = "SIGNO DESC";

    public static final String ORDER_BY_CODALM_ORIGEN = "CODALM_ORIGEN";

    public static final String ORDER_BY_CODALM_ORIGEN_DESC = "CODALM_ORIGEN DESC";

    public static final String ORDER_BY_CODALM_DESTINO = "CODALM_DESTINO";

    public static final String ORDER_BY_CODALM_DESTINO_DESC = "CODALM_DESTINO DESC";

    public static final String ORDER_BY_SOLICITUD_ACEPTACION_AUTO = "SOLICITUD_ACEPTACION_AUTO";

    public static final String ORDER_BY_SOLICITUD_ACEPTACION_AUTO_DESC = "SOLICITUD_ACEPTACION_AUTO DESC";

    public static final String ORDER_BY_SOLICITUD_RECEPCION_AUTO = "SOLICITUD_RECEPCION_AUTO";

    public static final String ORDER_BY_SOLICITUD_RECEPCION_AUTO_DESC = "SOLICITUD_RECEPCION_AUTO DESC";

    public static final String ORDER_BY_SOLICITUD_GENERAR_FALTAS = "SOLICITUD_GENERAR_FALTAS";

    public static final String ORDER_BY_SOLICITUD_GENERAR_FALTAS_DESC = "SOLICITUD_GENERAR_FALTAS DESC";

    public static final String ORDER_BY_VISIBLE_TIENDAS = "VISIBLE_TIENDAS";

    public static final String ORDER_BY_VISIBLE_TIENDAS_DESC = "VISIBLE_TIENDAS DESC";

    public static final String ORDER_BY_ACTIVO = "ACTIVO";

    public static final String ORDER_BY_ACTIVO_DESC = "ACTIVO DESC";

    public static final String ORDER_BY_OPERACION_ALMACEN = "OPERACION_ALMACEN";

    public static final String ORDER_BY_OPERACION_ALMACEN_DESC = "OPERACION_ALMACEN DESC";

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ConceptoAlmacenExample() {
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
        protected List<Criterion> solicitudAceptacionAutoCriteria;

        protected List<Criterion> solicitudRecepcionAutoCriteria;

        protected List<Criterion> solicitudGenerarFaltasCriteria;

        protected List<Criterion> visibleTiendasCriteria;

        protected List<Criterion> activoCriteria;

        protected List<Criterion> allCriteria;

        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
            solicitudAceptacionAutoCriteria = new ArrayList<Criterion>();
            solicitudRecepcionAutoCriteria = new ArrayList<Criterion>();
            solicitudGenerarFaltasCriteria = new ArrayList<Criterion>();
            visibleTiendasCriteria = new ArrayList<Criterion>();
            activoCriteria = new ArrayList<Criterion>();
        }

        public List<Criterion> getSolicitudAceptacionAutoCriteria() {
            return solicitudAceptacionAutoCriteria;
        }

        protected void addSolicitudAceptacionAutoCriterion(String condition, Object value, String property) {
            if (value != null) {
                solicitudAceptacionAutoCriteria.add(new Criterion(condition, value, "com.comerzzia.core.util.mybatis.typehandlers.BooleanTypeHandler"));
                allCriteria = null;
            }
        }

        protected void addSolicitudAceptacionAutoCriterion(String condition, Boolean value1, Boolean value2, String property) {
            if (value1 != null && value2 != null) {
                solicitudAceptacionAutoCriteria.add(new Criterion(condition, value1, value2, "com.comerzzia.core.util.mybatis.typehandlers.BooleanTypeHandler"));
                allCriteria = null;
            }
        }

        public List<Criterion> getSolicitudRecepcionAutoCriteria() {
            return solicitudRecepcionAutoCriteria;
        }

        protected void addSolicitudRecepcionAutoCriterion(String condition, Object value, String property) {
            if (value != null) {
                solicitudRecepcionAutoCriteria.add(new Criterion(condition, value, "com.comerzzia.core.util.mybatis.typehandlers.BooleanTypeHandler"));
                allCriteria = null;
            }
        }

        protected void addSolicitudRecepcionAutoCriterion(String condition, Boolean value1, Boolean value2, String property) {
            if (value1 != null && value2 != null) {
                solicitudRecepcionAutoCriteria.add(new Criterion(condition, value1, value2, "com.comerzzia.core.util.mybatis.typehandlers.BooleanTypeHandler"));
                allCriteria = null;
            }
        }

        public List<Criterion> getSolicitudGenerarFaltasCriteria() {
            return solicitudGenerarFaltasCriteria;
        }

        protected void addSolicitudGenerarFaltasCriterion(String condition, Object value, String property) {
            if (value != null) {
                solicitudGenerarFaltasCriteria.add(new Criterion(condition, value, "com.comerzzia.core.util.mybatis.typehandlers.BooleanTypeHandler"));
                allCriteria = null;
            }
        }

        protected void addSolicitudGenerarFaltasCriterion(String condition, Boolean value1, Boolean value2, String property) {
            if (value1 != null && value2 != null) {
                solicitudGenerarFaltasCriteria.add(new Criterion(condition, value1, value2, "com.comerzzia.core.util.mybatis.typehandlers.BooleanTypeHandler"));
                allCriteria = null;
            }
        }

        public List<Criterion> getVisibleTiendasCriteria() {
            return visibleTiendasCriteria;
        }

        protected void addVisibleTiendasCriterion(String condition, Object value, String property) {
            if (value != null) {
                visibleTiendasCriteria.add(new Criterion(condition, value, "com.comerzzia.core.util.mybatis.typehandlers.BooleanTypeHandler"));
                allCriteria = null;
            }
        }

        protected void addVisibleTiendasCriterion(String condition, Boolean value1, Boolean value2, String property) {
            if (value1 != null && value2 != null) {
                visibleTiendasCriteria.add(new Criterion(condition, value1, value2, "com.comerzzia.core.util.mybatis.typehandlers.BooleanTypeHandler"));
                allCriteria = null;
            }
        }

        public List<Criterion> getActivoCriteria() {
            return activoCriteria;
        }

        protected void addActivoCriterion(String condition, Object value, String property) {
            if (value != null) {
                activoCriteria.add(new Criterion(condition, value, "com.comerzzia.core.util.mybatis.typehandlers.BooleanTypeHandler"));
                allCriteria = null;
            }
        }

        protected void addActivoCriterion(String condition, Boolean value1, Boolean value2, String property) {
            if (value1 != null && value2 != null) {
                activoCriteria.add(new Criterion(condition, value1, value2, "com.comerzzia.core.util.mybatis.typehandlers.BooleanTypeHandler"));
                allCriteria = null;
            }
        }

        public boolean isValid() {
            return criteria.size() > 0
                || solicitudAceptacionAutoCriteria.size() > 0
                || solicitudRecepcionAutoCriteria.size() > 0
                || solicitudGenerarFaltasCriteria.size() > 0
                || visibleTiendasCriteria.size() > 0
                || activoCriteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            if (allCriteria == null) {
                allCriteria = new ArrayList<Criterion>();
                allCriteria.addAll(criteria);
                allCriteria.addAll(solicitudAceptacionAutoCriteria);
                allCriteria.addAll(solicitudRecepcionAutoCriteria);
                allCriteria.addAll(solicitudGenerarFaltasCriteria);
                allCriteria.addAll(visibleTiendasCriteria);
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

        public Criteria andDesconalmIsNull() {
            addCriterion("DESCONALM is null");
            return (Criteria) this;
        }

        public Criteria andDesconalmIsNotNull() {
            addCriterion("DESCONALM is not null");
            return (Criteria) this;
        }

        public Criteria andDesconalmEqualTo(String value) {
            addCriterion("DESCONALM =", value, "desconalm");
            return (Criteria) this;
        }

        public Criteria andDesconalmNotEqualTo(String value) {
            addCriterion("DESCONALM <>", value, "desconalm");
            return (Criteria) this;
        }

        public Criteria andDesconalmGreaterThan(String value) {
            addCriterion("DESCONALM >", value, "desconalm");
            return (Criteria) this;
        }

        public Criteria andDesconalmGreaterThanOrEqualTo(String value) {
            addCriterion("DESCONALM >=", value, "desconalm");
            return (Criteria) this;
        }

        public Criteria andDesconalmLessThan(String value) {
            addCriterion("DESCONALM <", value, "desconalm");
            return (Criteria) this;
        }

        public Criteria andDesconalmLessThanOrEqualTo(String value) {
            addCriterion("DESCONALM <=", value, "desconalm");
            return (Criteria) this;
        }

        public Criteria andDesconalmLike(String value) {
            addCriterion("DESCONALM like", value, "desconalm");
            return (Criteria) this;
        }

        public Criteria andDesconalmNotLike(String value) {
            addCriterion("DESCONALM not like", value, "desconalm");
            return (Criteria) this;
        }

        public Criteria andDesconalmIn(List<String> values) {
            addCriterion("DESCONALM in", values, "desconalm");
            return (Criteria) this;
        }

        public Criteria andDesconalmNotIn(List<String> values) {
            addCriterion("DESCONALM not in", values, "desconalm");
            return (Criteria) this;
        }

        public Criteria andDesconalmBetween(String value1, String value2) {
            addCriterion("DESCONALM between", value1, value2, "desconalm");
            return (Criteria) this;
        }

        public Criteria andDesconalmNotBetween(String value1, String value2) {
            addCriterion("DESCONALM not between", value1, value2, "desconalm");
            return (Criteria) this;
        }

        public Criteria andSignoIsNull() {
            addCriterion("SIGNO is null");
            return (Criteria) this;
        }

        public Criteria andSignoIsNotNull() {
            addCriterion("SIGNO is not null");
            return (Criteria) this;
        }

        public Criteria andSignoEqualTo(String value) {
            addCriterion("SIGNO =", value, "signo");
            return (Criteria) this;
        }

        public Criteria andSignoNotEqualTo(String value) {
            addCriterion("SIGNO <>", value, "signo");
            return (Criteria) this;
        }

        public Criteria andSignoGreaterThan(String value) {
            addCriterion("SIGNO >", value, "signo");
            return (Criteria) this;
        }

        public Criteria andSignoGreaterThanOrEqualTo(String value) {
            addCriterion("SIGNO >=", value, "signo");
            return (Criteria) this;
        }

        public Criteria andSignoLessThan(String value) {
            addCriterion("SIGNO <", value, "signo");
            return (Criteria) this;
        }

        public Criteria andSignoLessThanOrEqualTo(String value) {
            addCriterion("SIGNO <=", value, "signo");
            return (Criteria) this;
        }

        public Criteria andSignoLike(String value) {
            addCriterion("SIGNO like", value, "signo");
            return (Criteria) this;
        }

        public Criteria andSignoNotLike(String value) {
            addCriterion("SIGNO not like", value, "signo");
            return (Criteria) this;
        }

        public Criteria andSignoIn(List<String> values) {
            addCriterion("SIGNO in", values, "signo");
            return (Criteria) this;
        }

        public Criteria andSignoNotIn(List<String> values) {
            addCriterion("SIGNO not in", values, "signo");
            return (Criteria) this;
        }

        public Criteria andSignoBetween(String value1, String value2) {
            addCriterion("SIGNO between", value1, value2, "signo");
            return (Criteria) this;
        }

        public Criteria andSignoNotBetween(String value1, String value2) {
            addCriterion("SIGNO not between", value1, value2, "signo");
            return (Criteria) this;
        }

        public Criteria andCodalmOrigenIsNull() {
            addCriterion("CODALM_ORIGEN is null");
            return (Criteria) this;
        }

        public Criteria andCodalmOrigenIsNotNull() {
            addCriterion("CODALM_ORIGEN is not null");
            return (Criteria) this;
        }

        public Criteria andCodalmOrigenEqualTo(String value) {
            addCriterion("CODALM_ORIGEN =", value, "codalmOrigen");
            return (Criteria) this;
        }

        public Criteria andCodalmOrigenNotEqualTo(String value) {
            addCriterion("CODALM_ORIGEN <>", value, "codalmOrigen");
            return (Criteria) this;
        }

        public Criteria andCodalmOrigenGreaterThan(String value) {
            addCriterion("CODALM_ORIGEN >", value, "codalmOrigen");
            return (Criteria) this;
        }

        public Criteria andCodalmOrigenGreaterThanOrEqualTo(String value) {
            addCriterion("CODALM_ORIGEN >=", value, "codalmOrigen");
            return (Criteria) this;
        }

        public Criteria andCodalmOrigenLessThan(String value) {
            addCriterion("CODALM_ORIGEN <", value, "codalmOrigen");
            return (Criteria) this;
        }

        public Criteria andCodalmOrigenLessThanOrEqualTo(String value) {
            addCriterion("CODALM_ORIGEN <=", value, "codalmOrigen");
            return (Criteria) this;
        }

        public Criteria andCodalmOrigenLike(String value) {
            addCriterion("CODALM_ORIGEN like", value, "codalmOrigen");
            return (Criteria) this;
        }

        public Criteria andCodalmOrigenNotLike(String value) {
            addCriterion("CODALM_ORIGEN not like", value, "codalmOrigen");
            return (Criteria) this;
        }

        public Criteria andCodalmOrigenIn(List<String> values) {
            addCriterion("CODALM_ORIGEN in", values, "codalmOrigen");
            return (Criteria) this;
        }

        public Criteria andCodalmOrigenNotIn(List<String> values) {
            addCriterion("CODALM_ORIGEN not in", values, "codalmOrigen");
            return (Criteria) this;
        }

        public Criteria andCodalmOrigenBetween(String value1, String value2) {
            addCriterion("CODALM_ORIGEN between", value1, value2, "codalmOrigen");
            return (Criteria) this;
        }

        public Criteria andCodalmOrigenNotBetween(String value1, String value2) {
            addCriterion("CODALM_ORIGEN not between", value1, value2, "codalmOrigen");
            return (Criteria) this;
        }

        public Criteria andCodalmDestinoIsNull() {
            addCriterion("CODALM_DESTINO is null");
            return (Criteria) this;
        }

        public Criteria andCodalmDestinoIsNotNull() {
            addCriterion("CODALM_DESTINO is not null");
            return (Criteria) this;
        }

        public Criteria andCodalmDestinoEqualTo(String value) {
            addCriterion("CODALM_DESTINO =", value, "codalmDestino");
            return (Criteria) this;
        }

        public Criteria andCodalmDestinoNotEqualTo(String value) {
            addCriterion("CODALM_DESTINO <>", value, "codalmDestino");
            return (Criteria) this;
        }

        public Criteria andCodalmDestinoGreaterThan(String value) {
            addCriterion("CODALM_DESTINO >", value, "codalmDestino");
            return (Criteria) this;
        }

        public Criteria andCodalmDestinoGreaterThanOrEqualTo(String value) {
            addCriterion("CODALM_DESTINO >=", value, "codalmDestino");
            return (Criteria) this;
        }

        public Criteria andCodalmDestinoLessThan(String value) {
            addCriterion("CODALM_DESTINO <", value, "codalmDestino");
            return (Criteria) this;
        }

        public Criteria andCodalmDestinoLessThanOrEqualTo(String value) {
            addCriterion("CODALM_DESTINO <=", value, "codalmDestino");
            return (Criteria) this;
        }

        public Criteria andCodalmDestinoLike(String value) {
            addCriterion("CODALM_DESTINO like", value, "codalmDestino");
            return (Criteria) this;
        }

        public Criteria andCodalmDestinoNotLike(String value) {
            addCriterion("CODALM_DESTINO not like", value, "codalmDestino");
            return (Criteria) this;
        }

        public Criteria andCodalmDestinoIn(List<String> values) {
            addCriterion("CODALM_DESTINO in", values, "codalmDestino");
            return (Criteria) this;
        }

        public Criteria andCodalmDestinoNotIn(List<String> values) {
            addCriterion("CODALM_DESTINO not in", values, "codalmDestino");
            return (Criteria) this;
        }

        public Criteria andCodalmDestinoBetween(String value1, String value2) {
            addCriterion("CODALM_DESTINO between", value1, value2, "codalmDestino");
            return (Criteria) this;
        }

        public Criteria andCodalmDestinoNotBetween(String value1, String value2) {
            addCriterion("CODALM_DESTINO not between", value1, value2, "codalmDestino");
            return (Criteria) this;
        }

        public Criteria andSolicitudAceptacionAutoIsNull() {
            addCriterion("SOLICITUD_ACEPTACION_AUTO is null");
            return (Criteria) this;
        }

        public Criteria andSolicitudAceptacionAutoIsNotNull() {
            addCriterion("SOLICITUD_ACEPTACION_AUTO is not null");
            return (Criteria) this;
        }

        public Criteria andSolicitudAceptacionAutoEqualTo(Boolean value) {
            addSolicitudAceptacionAutoCriterion("SOLICITUD_ACEPTACION_AUTO =", value, "solicitudAceptacionAuto");
            return (Criteria) this;
        }

        public Criteria andSolicitudAceptacionAutoNotEqualTo(Boolean value) {
            addSolicitudAceptacionAutoCriterion("SOLICITUD_ACEPTACION_AUTO <>", value, "solicitudAceptacionAuto");
            return (Criteria) this;
        }

        public Criteria andSolicitudAceptacionAutoGreaterThan(Boolean value) {
            addSolicitudAceptacionAutoCriterion("SOLICITUD_ACEPTACION_AUTO >", value, "solicitudAceptacionAuto");
            return (Criteria) this;
        }

        public Criteria andSolicitudAceptacionAutoGreaterThanOrEqualTo(Boolean value) {
            addSolicitudAceptacionAutoCriterion("SOLICITUD_ACEPTACION_AUTO >=", value, "solicitudAceptacionAuto");
            return (Criteria) this;
        }

        public Criteria andSolicitudAceptacionAutoLessThan(Boolean value) {
            addSolicitudAceptacionAutoCriterion("SOLICITUD_ACEPTACION_AUTO <", value, "solicitudAceptacionAuto");
            return (Criteria) this;
        }

        public Criteria andSolicitudAceptacionAutoLessThanOrEqualTo(Boolean value) {
            addSolicitudAceptacionAutoCriterion("SOLICITUD_ACEPTACION_AUTO <=", value, "solicitudAceptacionAuto");
            return (Criteria) this;
        }

        public Criteria andSolicitudAceptacionAutoLike(Boolean value) {
            addSolicitudAceptacionAutoCriterion("SOLICITUD_ACEPTACION_AUTO like", value, "solicitudAceptacionAuto");
            return (Criteria) this;
        }

        public Criteria andSolicitudAceptacionAutoNotLike(Boolean value) {
            addSolicitudAceptacionAutoCriterion("SOLICITUD_ACEPTACION_AUTO not like", value, "solicitudAceptacionAuto");
            return (Criteria) this;
        }

        public Criteria andSolicitudAceptacionAutoIn(List<Boolean> values) {
            addSolicitudAceptacionAutoCriterion("SOLICITUD_ACEPTACION_AUTO in", values, "solicitudAceptacionAuto");
            return (Criteria) this;
        }

        public Criteria andSolicitudAceptacionAutoNotIn(List<Boolean> values) {
            addSolicitudAceptacionAutoCriterion("SOLICITUD_ACEPTACION_AUTO not in", values, "solicitudAceptacionAuto");
            return (Criteria) this;
        }

        public Criteria andSolicitudAceptacionAutoBetween(Boolean value1, Boolean value2) {
            addSolicitudAceptacionAutoCriterion("SOLICITUD_ACEPTACION_AUTO between", value1, value2, "solicitudAceptacionAuto");
            return (Criteria) this;
        }

        public Criteria andSolicitudAceptacionAutoNotBetween(Boolean value1, Boolean value2) {
            addSolicitudAceptacionAutoCriterion("SOLICITUD_ACEPTACION_AUTO not between", value1, value2, "solicitudAceptacionAuto");
            return (Criteria) this;
        }

        public Criteria andSolicitudRecepcionAutoIsNull() {
            addCriterion("SOLICITUD_RECEPCION_AUTO is null");
            return (Criteria) this;
        }

        public Criteria andSolicitudRecepcionAutoIsNotNull() {
            addCriterion("SOLICITUD_RECEPCION_AUTO is not null");
            return (Criteria) this;
        }

        public Criteria andSolicitudRecepcionAutoEqualTo(Boolean value) {
            addSolicitudRecepcionAutoCriterion("SOLICITUD_RECEPCION_AUTO =", value, "solicitudRecepcionAuto");
            return (Criteria) this;
        }

        public Criteria andSolicitudRecepcionAutoNotEqualTo(Boolean value) {
            addSolicitudRecepcionAutoCriterion("SOLICITUD_RECEPCION_AUTO <>", value, "solicitudRecepcionAuto");
            return (Criteria) this;
        }

        public Criteria andSolicitudRecepcionAutoGreaterThan(Boolean value) {
            addSolicitudRecepcionAutoCriterion("SOLICITUD_RECEPCION_AUTO >", value, "solicitudRecepcionAuto");
            return (Criteria) this;
        }

        public Criteria andSolicitudRecepcionAutoGreaterThanOrEqualTo(Boolean value) {
            addSolicitudRecepcionAutoCriterion("SOLICITUD_RECEPCION_AUTO >=", value, "solicitudRecepcionAuto");
            return (Criteria) this;
        }

        public Criteria andSolicitudRecepcionAutoLessThan(Boolean value) {
            addSolicitudRecepcionAutoCriterion("SOLICITUD_RECEPCION_AUTO <", value, "solicitudRecepcionAuto");
            return (Criteria) this;
        }

        public Criteria andSolicitudRecepcionAutoLessThanOrEqualTo(Boolean value) {
            addSolicitudRecepcionAutoCriterion("SOLICITUD_RECEPCION_AUTO <=", value, "solicitudRecepcionAuto");
            return (Criteria) this;
        }

        public Criteria andSolicitudRecepcionAutoLike(Boolean value) {
            addSolicitudRecepcionAutoCriterion("SOLICITUD_RECEPCION_AUTO like", value, "solicitudRecepcionAuto");
            return (Criteria) this;
        }

        public Criteria andSolicitudRecepcionAutoNotLike(Boolean value) {
            addSolicitudRecepcionAutoCriterion("SOLICITUD_RECEPCION_AUTO not like", value, "solicitudRecepcionAuto");
            return (Criteria) this;
        }

        public Criteria andSolicitudRecepcionAutoIn(List<Boolean> values) {
            addSolicitudRecepcionAutoCriterion("SOLICITUD_RECEPCION_AUTO in", values, "solicitudRecepcionAuto");
            return (Criteria) this;
        }

        public Criteria andSolicitudRecepcionAutoNotIn(List<Boolean> values) {
            addSolicitudRecepcionAutoCriterion("SOLICITUD_RECEPCION_AUTO not in", values, "solicitudRecepcionAuto");
            return (Criteria) this;
        }

        public Criteria andSolicitudRecepcionAutoBetween(Boolean value1, Boolean value2) {
            addSolicitudRecepcionAutoCriterion("SOLICITUD_RECEPCION_AUTO between", value1, value2, "solicitudRecepcionAuto");
            return (Criteria) this;
        }

        public Criteria andSolicitudRecepcionAutoNotBetween(Boolean value1, Boolean value2) {
            addSolicitudRecepcionAutoCriterion("SOLICITUD_RECEPCION_AUTO not between", value1, value2, "solicitudRecepcionAuto");
            return (Criteria) this;
        }

        public Criteria andSolicitudGenerarFaltasIsNull() {
            addCriterion("SOLICITUD_GENERAR_FALTAS is null");
            return (Criteria) this;
        }

        public Criteria andSolicitudGenerarFaltasIsNotNull() {
            addCriterion("SOLICITUD_GENERAR_FALTAS is not null");
            return (Criteria) this;
        }

        public Criteria andSolicitudGenerarFaltasEqualTo(Boolean value) {
            addSolicitudGenerarFaltasCriterion("SOLICITUD_GENERAR_FALTAS =", value, "solicitudGenerarFaltas");
            return (Criteria) this;
        }

        public Criteria andSolicitudGenerarFaltasNotEqualTo(Boolean value) {
            addSolicitudGenerarFaltasCriterion("SOLICITUD_GENERAR_FALTAS <>", value, "solicitudGenerarFaltas");
            return (Criteria) this;
        }

        public Criteria andSolicitudGenerarFaltasGreaterThan(Boolean value) {
            addSolicitudGenerarFaltasCriterion("SOLICITUD_GENERAR_FALTAS >", value, "solicitudGenerarFaltas");
            return (Criteria) this;
        }

        public Criteria andSolicitudGenerarFaltasGreaterThanOrEqualTo(Boolean value) {
            addSolicitudGenerarFaltasCriterion("SOLICITUD_GENERAR_FALTAS >=", value, "solicitudGenerarFaltas");
            return (Criteria) this;
        }

        public Criteria andSolicitudGenerarFaltasLessThan(Boolean value) {
            addSolicitudGenerarFaltasCriterion("SOLICITUD_GENERAR_FALTAS <", value, "solicitudGenerarFaltas");
            return (Criteria) this;
        }

        public Criteria andSolicitudGenerarFaltasLessThanOrEqualTo(Boolean value) {
            addSolicitudGenerarFaltasCriterion("SOLICITUD_GENERAR_FALTAS <=", value, "solicitudGenerarFaltas");
            return (Criteria) this;
        }

        public Criteria andSolicitudGenerarFaltasLike(Boolean value) {
            addSolicitudGenerarFaltasCriterion("SOLICITUD_GENERAR_FALTAS like", value, "solicitudGenerarFaltas");
            return (Criteria) this;
        }

        public Criteria andSolicitudGenerarFaltasNotLike(Boolean value) {
            addSolicitudGenerarFaltasCriterion("SOLICITUD_GENERAR_FALTAS not like", value, "solicitudGenerarFaltas");
            return (Criteria) this;
        }

        public Criteria andSolicitudGenerarFaltasIn(List<Boolean> values) {
            addSolicitudGenerarFaltasCriterion("SOLICITUD_GENERAR_FALTAS in", values, "solicitudGenerarFaltas");
            return (Criteria) this;
        }

        public Criteria andSolicitudGenerarFaltasNotIn(List<Boolean> values) {
            addSolicitudGenerarFaltasCriterion("SOLICITUD_GENERAR_FALTAS not in", values, "solicitudGenerarFaltas");
            return (Criteria) this;
        }

        public Criteria andSolicitudGenerarFaltasBetween(Boolean value1, Boolean value2) {
            addSolicitudGenerarFaltasCriterion("SOLICITUD_GENERAR_FALTAS between", value1, value2, "solicitudGenerarFaltas");
            return (Criteria) this;
        }

        public Criteria andSolicitudGenerarFaltasNotBetween(Boolean value1, Boolean value2) {
            addSolicitudGenerarFaltasCriterion("SOLICITUD_GENERAR_FALTAS not between", value1, value2, "solicitudGenerarFaltas");
            return (Criteria) this;
        }

        public Criteria andVisibleTiendasIsNull() {
            addCriterion("VISIBLE_TIENDAS is null");
            return (Criteria) this;
        }

        public Criteria andVisibleTiendasIsNotNull() {
            addCriterion("VISIBLE_TIENDAS is not null");
            return (Criteria) this;
        }

        public Criteria andVisibleTiendasEqualTo(Boolean value) {
            addVisibleTiendasCriterion("VISIBLE_TIENDAS =", value, "visibleTiendas");
            return (Criteria) this;
        }

        public Criteria andVisibleTiendasNotEqualTo(Boolean value) {
            addVisibleTiendasCriterion("VISIBLE_TIENDAS <>", value, "visibleTiendas");
            return (Criteria) this;
        }

        public Criteria andVisibleTiendasGreaterThan(Boolean value) {
            addVisibleTiendasCriterion("VISIBLE_TIENDAS >", value, "visibleTiendas");
            return (Criteria) this;
        }

        public Criteria andVisibleTiendasGreaterThanOrEqualTo(Boolean value) {
            addVisibleTiendasCriterion("VISIBLE_TIENDAS >=", value, "visibleTiendas");
            return (Criteria) this;
        }

        public Criteria andVisibleTiendasLessThan(Boolean value) {
            addVisibleTiendasCriterion("VISIBLE_TIENDAS <", value, "visibleTiendas");
            return (Criteria) this;
        }

        public Criteria andVisibleTiendasLessThanOrEqualTo(Boolean value) {
            addVisibleTiendasCriterion("VISIBLE_TIENDAS <=", value, "visibleTiendas");
            return (Criteria) this;
        }

        public Criteria andVisibleTiendasLike(Boolean value) {
            addVisibleTiendasCriterion("VISIBLE_TIENDAS like", value, "visibleTiendas");
            return (Criteria) this;
        }

        public Criteria andVisibleTiendasNotLike(Boolean value) {
            addVisibleTiendasCriterion("VISIBLE_TIENDAS not like", value, "visibleTiendas");
            return (Criteria) this;
        }

        public Criteria andVisibleTiendasIn(List<Boolean> values) {
            addVisibleTiendasCriterion("VISIBLE_TIENDAS in", values, "visibleTiendas");
            return (Criteria) this;
        }

        public Criteria andVisibleTiendasNotIn(List<Boolean> values) {
            addVisibleTiendasCriterion("VISIBLE_TIENDAS not in", values, "visibleTiendas");
            return (Criteria) this;
        }

        public Criteria andVisibleTiendasBetween(Boolean value1, Boolean value2) {
            addVisibleTiendasCriterion("VISIBLE_TIENDAS between", value1, value2, "visibleTiendas");
            return (Criteria) this;
        }

        public Criteria andVisibleTiendasNotBetween(Boolean value1, Boolean value2) {
            addVisibleTiendasCriterion("VISIBLE_TIENDAS not between", value1, value2, "visibleTiendas");
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

        public Criteria andOperacionAlmacenIsNull() {
            addCriterion("OPERACION_ALMACEN is null");
            return (Criteria) this;
        }

        public Criteria andOperacionAlmacenIsNotNull() {
            addCriterion("OPERACION_ALMACEN is not null");
            return (Criteria) this;
        }

        public Criteria andOperacionAlmacenEqualTo(Short value) {
            addCriterion("OPERACION_ALMACEN =", value, "operacionAlmacen");
            return (Criteria) this;
        }

        public Criteria andOperacionAlmacenNotEqualTo(Short value) {
            addCriterion("OPERACION_ALMACEN <>", value, "operacionAlmacen");
            return (Criteria) this;
        }

        public Criteria andOperacionAlmacenGreaterThan(Short value) {
            addCriterion("OPERACION_ALMACEN >", value, "operacionAlmacen");
            return (Criteria) this;
        }

        public Criteria andOperacionAlmacenGreaterThanOrEqualTo(Short value) {
            addCriterion("OPERACION_ALMACEN >=", value, "operacionAlmacen");
            return (Criteria) this;
        }

        public Criteria andOperacionAlmacenLessThan(Short value) {
            addCriterion("OPERACION_ALMACEN <", value, "operacionAlmacen");
            return (Criteria) this;
        }

        public Criteria andOperacionAlmacenLessThanOrEqualTo(Short value) {
            addCriterion("OPERACION_ALMACEN <=", value, "operacionAlmacen");
            return (Criteria) this;
        }

        public Criteria andOperacionAlmacenIn(List<Short> values) {
            addCriterion("OPERACION_ALMACEN in", values, "operacionAlmacen");
            return (Criteria) this;
        }

        public Criteria andOperacionAlmacenNotIn(List<Short> values) {
            addCriterion("OPERACION_ALMACEN not in", values, "operacionAlmacen");
            return (Criteria) this;
        }

        public Criteria andOperacionAlmacenBetween(Short value1, Short value2) {
            addCriterion("OPERACION_ALMACEN between", value1, value2, "operacionAlmacen");
            return (Criteria) this;
        }

        public Criteria andOperacionAlmacenNotBetween(Short value1, Short value2) {
            addCriterion("OPERACION_ALMACEN not between", value1, value2, "operacionAlmacen");
            return (Criteria) this;
        }

        public Criteria andUidActividadLikeInsensitive(String value) {
            addCriterion("upper(UID_ACTIVIDAD) like", value.toUpperCase(), "uidActividad");
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

        public Criteria andDesconalmLikeInsensitive(String value) {
            addCriterion("upper(DESCONALM) like", value.toUpperCase(), "desconalm");
            return (Criteria) this;
        }

        public Criteria andSignoLikeInsensitive(String value) {
            addCriterion("upper(SIGNO) like", value.toUpperCase(), "signo");
            return (Criteria) this;
        }

        public Criteria andCodalmOrigenLikeInsensitive(String value) {
            addCriterion("upper(CODALM_ORIGEN) like", value.toUpperCase(), "codalmOrigen");
            return (Criteria) this;
        }

        public Criteria andCodalmDestinoLikeInsensitive(String value) {
            addCriterion("upper(CODALM_DESTINO) like", value.toUpperCase(), "codalmDestino");
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