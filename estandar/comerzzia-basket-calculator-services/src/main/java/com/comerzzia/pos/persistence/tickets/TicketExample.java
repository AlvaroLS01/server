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
package com.comerzzia.pos.persistence.tickets;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TicketExample {
    public static final String ORDER_BY_UID_ACTIVIDAD = "UID_ACTIVIDAD";

    public static final String ORDER_BY_UID_ACTIVIDAD_DESC = "UID_ACTIVIDAD DESC";

    public static final String ORDER_BY_UID_TICKET = "UID_TICKET";

    public static final String ORDER_BY_UID_TICKET_DESC = "UID_TICKET DESC";

    public static final String ORDER_BY_CODALM = "CODALM";

    public static final String ORDER_BY_CODALM_DESC = "CODALM DESC";

    public static final String ORDER_BY_ID_TICKET = "ID_TICKET";

    public static final String ORDER_BY_ID_TICKET_DESC = "ID_TICKET DESC";

    public static final String ORDER_BY_FECHA = "FECHA";

    public static final String ORDER_BY_FECHA_DESC = "FECHA DESC";

    public static final String ORDER_BY_PROCESADO = "PROCESADO";

    public static final String ORDER_BY_PROCESADO_DESC = "PROCESADO DESC";

    public static final String ORDER_BY_FECHA_PROCESO = "FECHA_PROCESO";

    public static final String ORDER_BY_FECHA_PROCESO_DESC = "FECHA_PROCESO DESC";

    public static final String ORDER_BY_MENSAJE_PROCESO = "MENSAJE_PROCESO";

    public static final String ORDER_BY_MENSAJE_PROCESO_DESC = "MENSAJE_PROCESO DESC";

    public static final String ORDER_BY_CODCAJA = "CODCAJA";

    public static final String ORDER_BY_CODCAJA_DESC = "CODCAJA DESC";

    public static final String ORDER_BY_ID_TIPO_DOCUMENTO = "ID_TIPO_DOCUMENTO";

    public static final String ORDER_BY_ID_TIPO_DOCUMENTO_DESC = "ID_TIPO_DOCUMENTO DESC";

    public static final String ORDER_BY_COD_TICKET = "COD_TICKET";

    public static final String ORDER_BY_COD_TICKET_DESC = "COD_TICKET DESC";

    public static final String ORDER_BY_FIRMA = "FIRMA";

    public static final String ORDER_BY_FIRMA_DESC = "FIRMA DESC";

    public static final String ORDER_BY_TICKET = "TICKET";

    public static final String ORDER_BY_TICKET_DESC = "TICKET DESC";

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TicketExample() {
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
        protected List<Criterion> procesadoCriteria;

        protected List<Criterion> allCriteria;

        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
            procesadoCriteria = new ArrayList<Criterion>();
        }

        public List<Criterion> getProcesadoCriteria() {
            return procesadoCriteria;
        }

        protected void addProcesadoCriterion(String condition, Object value, String property) {
            if (value != null) {
                procesadoCriteria.add(new Criterion(condition, value, "com.comerzzia.core.util.mybatis.typehandlers.BooleanStringTypeHandler"));
                allCriteria = null;
            }
        }

        protected void addProcesadoCriterion(String condition, Boolean value1, Boolean value2, String property) {
            if (value1 != null && value2 != null) {
                procesadoCriteria.add(new Criterion(condition, value1, value2, "com.comerzzia.core.util.mybatis.typehandlers.BooleanStringTypeHandler"));
                allCriteria = null;
            }
        }

        public boolean isValid() {
            return criteria.size() > 0
                || procesadoCriteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            if (allCriteria == null) {
                allCriteria = new ArrayList<Criterion>();
                allCriteria.addAll(criteria);
                allCriteria.addAll(procesadoCriteria);
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

        public Criteria andUidTicketIsNull() {
            addCriterion("UID_TICKET is null");
            return (Criteria) this;
        }

        public Criteria andUidTicketIsNotNull() {
            addCriterion("UID_TICKET is not null");
            return (Criteria) this;
        }

        public Criteria andUidTicketEqualTo(String value) {
            addCriterion("UID_TICKET =", value, "uidTicket");
            return (Criteria) this;
        }

        public Criteria andUidTicketNotEqualTo(String value) {
            addCriterion("UID_TICKET <>", value, "uidTicket");
            return (Criteria) this;
        }

        public Criteria andUidTicketGreaterThan(String value) {
            addCriterion("UID_TICKET >", value, "uidTicket");
            return (Criteria) this;
        }

        public Criteria andUidTicketGreaterThanOrEqualTo(String value) {
            addCriterion("UID_TICKET >=", value, "uidTicket");
            return (Criteria) this;
        }

        public Criteria andUidTicketLessThan(String value) {
            addCriterion("UID_TICKET <", value, "uidTicket");
            return (Criteria) this;
        }

        public Criteria andUidTicketLessThanOrEqualTo(String value) {
            addCriterion("UID_TICKET <=", value, "uidTicket");
            return (Criteria) this;
        }

        public Criteria andUidTicketLike(String value) {
            addCriterion("UID_TICKET like", value, "uidTicket");
            return (Criteria) this;
        }

        public Criteria andUidTicketNotLike(String value) {
            addCriterion("UID_TICKET not like", value, "uidTicket");
            return (Criteria) this;
        }

        public Criteria andUidTicketIn(List<String> values) {
            addCriterion("UID_TICKET in", values, "uidTicket");
            return (Criteria) this;
        }

        public Criteria andUidTicketNotIn(List<String> values) {
            addCriterion("UID_TICKET not in", values, "uidTicket");
            return (Criteria) this;
        }

        public Criteria andUidTicketBetween(String value1, String value2) {
            addCriterion("UID_TICKET between", value1, value2, "uidTicket");
            return (Criteria) this;
        }

        public Criteria andUidTicketNotBetween(String value1, String value2) {
            addCriterion("UID_TICKET not between", value1, value2, "uidTicket");
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

        public Criteria andIdTicketIsNull() {
            addCriterion("ID_TICKET is null");
            return (Criteria) this;
        }

        public Criteria andIdTicketIsNotNull() {
            addCriterion("ID_TICKET is not null");
            return (Criteria) this;
        }
        
        public Criteria andSerieEqualTo(String value) {
            addCriterion("SERIE_TICKET =", value, "serie_ticket");
            return (Criteria) this;
        }

        public Criteria andIdTicketEqualTo(Long value) {
            addCriterion("ID_TICKET =", value, "idTicket");
            return (Criteria) this;
        }

        public Criteria andIdTicketNotEqualTo(Long value) {
            addCriterion("ID_TICKET <>", value, "idTicket");
            return (Criteria) this;
        }

        public Criteria andIdTicketGreaterThan(Long value) {
            addCriterion("ID_TICKET >", value, "idTicket");
            return (Criteria) this;
        }

        public Criteria andIdTicketGreaterThanOrEqualTo(Long value) {
            addCriterion("ID_TICKET >=", value, "idTicket");
            return (Criteria) this;
        }

        public Criteria andIdTicketLessThan(Long value) {
            addCriterion("ID_TICKET <", value, "idTicket");
            return (Criteria) this;
        }

        public Criteria andIdTicketLessThanOrEqualTo(Long value) {
            addCriterion("ID_TICKET <=", value, "idTicket");
            return (Criteria) this;
        }

        public Criteria andIdTicketIn(List<Long> values) {
            addCriterion("ID_TICKET in", values, "idTicket");
            return (Criteria) this;
        }

        public Criteria andIdTicketNotIn(List<Long> values) {
            addCriterion("ID_TICKET not in", values, "idTicket");
            return (Criteria) this;
        }

        public Criteria andIdTicketBetween(Long value1, Long value2) {
            addCriterion("ID_TICKET between", value1, value2, "idTicket");
            return (Criteria) this;
        }

        public Criteria andIdTicketNotBetween(Long value1, Long value2) {
            addCriterion("ID_TICKET not between", value1, value2, "idTicket");
            return (Criteria) this;
        }

        public Criteria andFechaIsNull() {
            addCriterion("FECHA is null");
            return (Criteria) this;
        }

        public Criteria andFechaIsNotNull() {
            addCriterion("FECHA is not null");
            return (Criteria) this;
        }

        public Criteria andFechaEqualTo(Date value) {
            addCriterion("FECHA =", value, "fecha");
            return (Criteria) this;
        }

        public Criteria andFechaNotEqualTo(Date value) {
            addCriterion("FECHA <>", value, "fecha");
            return (Criteria) this;
        }

        public Criteria andFechaGreaterThan(Date value) {
            addCriterion("FECHA >", value, "fecha");
            return (Criteria) this;
        }

        public Criteria andFechaGreaterThanOrEqualTo(Date value) {
            addCriterion("FECHA >=", value, "fecha");
            return (Criteria) this;
        }

        public Criteria andFechaLessThan(Date value) {
            addCriterion("FECHA <", value, "fecha");
            return (Criteria) this;
        }

        public Criteria andFechaLessThanOrEqualTo(Date value) {
            addCriterion("FECHA <=", value, "fecha");
            return (Criteria) this;
        }

        public Criteria andFechaIn(List<Date> values) {
            addCriterion("FECHA in", values, "fecha");
            return (Criteria) this;
        }

        public Criteria andFechaNotIn(List<Date> values) {
            addCriterion("FECHA not in", values, "fecha");
            return (Criteria) this;
        }

        public Criteria andFechaBetween(Date value1, Date value2) {
            addCriterion("FECHA between", value1, value2, "fecha");
            return (Criteria) this;
        }

        public Criteria andFechaNotBetween(Date value1, Date value2) {
            addCriterion("FECHA not between", value1, value2, "fecha");
            return (Criteria) this;
        }

        public Criteria andProcesadoIsNull() {
            addCriterion("PROCESADO is null");
            return (Criteria) this;
        }

        public Criteria andProcesadoIsNotNull() {
            addCriterion("PROCESADO is not null");
            return (Criteria) this;
        }

        public Criteria andProcesadoEqualTo(Boolean value) {
            addProcesadoCriterion("PROCESADO =", value, "procesado");
            return (Criteria) this;
        }

        public Criteria andProcesadoNotEqualTo(Boolean value) {
            addProcesadoCriterion("PROCESADO <>", value, "procesado");
            return (Criteria) this;
        }

        public Criteria andProcesadoGreaterThan(Boolean value) {
            addProcesadoCriterion("PROCESADO >", value, "procesado");
            return (Criteria) this;
        }

        public Criteria andProcesadoGreaterThanOrEqualTo(Boolean value) {
            addProcesadoCriterion("PROCESADO >=", value, "procesado");
            return (Criteria) this;
        }

        public Criteria andProcesadoLessThan(Boolean value) {
            addProcesadoCriterion("PROCESADO <", value, "procesado");
            return (Criteria) this;
        }

        public Criteria andProcesadoLessThanOrEqualTo(Boolean value) {
            addProcesadoCriterion("PROCESADO <=", value, "procesado");
            return (Criteria) this;
        }

        public Criteria andProcesadoLike(Boolean value) {
            addProcesadoCriterion("PROCESADO like", value, "procesado");
            return (Criteria) this;
        }

        public Criteria andProcesadoNotLike(Boolean value) {
            addProcesadoCriterion("PROCESADO not like", value, "procesado");
            return (Criteria) this;
        }

        public Criteria andProcesadoIn(List<Boolean> values) {
            addProcesadoCriterion("PROCESADO in", values, "procesado");
            return (Criteria) this;
        }

        public Criteria andProcesadoNotIn(List<Boolean> values) {
            addProcesadoCriterion("PROCESADO not in", values, "procesado");
            return (Criteria) this;
        }

        public Criteria andProcesadoBetween(Boolean value1, Boolean value2) {
            addProcesadoCriterion("PROCESADO between", value1, value2, "procesado");
            return (Criteria) this;
        }

        public Criteria andProcesadoNotBetween(Boolean value1, Boolean value2) {
            addProcesadoCriterion("PROCESADO not between", value1, value2, "procesado");
            return (Criteria) this;
        }

        public Criteria andFechaProcesoIsNull() {
            addCriterion("FECHA_PROCESO is null");
            return (Criteria) this;
        }

        public Criteria andFechaProcesoIsNotNull() {
            addCriterion("FECHA_PROCESO is not null");
            return (Criteria) this;
        }

        public Criteria andFechaProcesoEqualTo(Date value) {
            addCriterion("FECHA_PROCESO =", value, "fechaProceso");
            return (Criteria) this;
        }

        public Criteria andFechaProcesoNotEqualTo(Date value) {
            addCriterion("FECHA_PROCESO <>", value, "fechaProceso");
            return (Criteria) this;
        }

        public Criteria andFechaProcesoGreaterThan(Date value) {
            addCriterion("FECHA_PROCESO >", value, "fechaProceso");
            return (Criteria) this;
        }

        public Criteria andFechaProcesoGreaterThanOrEqualTo(Date value) {
            addCriterion("FECHA_PROCESO >=", value, "fechaProceso");
            return (Criteria) this;
        }

        public Criteria andFechaProcesoLessThan(Date value) {
            addCriterion("FECHA_PROCESO <", value, "fechaProceso");
            return (Criteria) this;
        }

        public Criteria andFechaProcesoLessThanOrEqualTo(Date value) {
            addCriterion("FECHA_PROCESO <=", value, "fechaProceso");
            return (Criteria) this;
        }

        public Criteria andFechaProcesoIn(List<Date> values) {
            addCriterion("FECHA_PROCESO in", values, "fechaProceso");
            return (Criteria) this;
        }

        public Criteria andFechaProcesoNotIn(List<Date> values) {
            addCriterion("FECHA_PROCESO not in", values, "fechaProceso");
            return (Criteria) this;
        }

        public Criteria andFechaProcesoBetween(Date value1, Date value2) {
            addCriterion("FECHA_PROCESO between", value1, value2, "fechaProceso");
            return (Criteria) this;
        }

        public Criteria andFechaProcesoNotBetween(Date value1, Date value2) {
            addCriterion("FECHA_PROCESO not between", value1, value2, "fechaProceso");
            return (Criteria) this;
        }

        public Criteria andMensajeProcesoIsNull() {
            addCriterion("MENSAJE_PROCESO is null");
            return (Criteria) this;
        }

        public Criteria andMensajeProcesoIsNotNull() {
            addCriterion("MENSAJE_PROCESO is not null");
            return (Criteria) this;
        }

        public Criteria andMensajeProcesoEqualTo(String value) {
            addCriterion("MENSAJE_PROCESO =", value, "mensajeProceso");
            return (Criteria) this;
        }

        public Criteria andMensajeProcesoNotEqualTo(String value) {
            addCriterion("MENSAJE_PROCESO <>", value, "mensajeProceso");
            return (Criteria) this;
        }

        public Criteria andMensajeProcesoGreaterThan(String value) {
            addCriterion("MENSAJE_PROCESO >", value, "mensajeProceso");
            return (Criteria) this;
        }

        public Criteria andMensajeProcesoGreaterThanOrEqualTo(String value) {
            addCriterion("MENSAJE_PROCESO >=", value, "mensajeProceso");
            return (Criteria) this;
        }

        public Criteria andMensajeProcesoLessThan(String value) {
            addCriterion("MENSAJE_PROCESO <", value, "mensajeProceso");
            return (Criteria) this;
        }

        public Criteria andMensajeProcesoLessThanOrEqualTo(String value) {
            addCriterion("MENSAJE_PROCESO <=", value, "mensajeProceso");
            return (Criteria) this;
        }

        public Criteria andMensajeProcesoLike(String value) {
            addCriterion("MENSAJE_PROCESO like", value, "mensajeProceso");
            return (Criteria) this;
        }

        public Criteria andMensajeProcesoNotLike(String value) {
            addCriterion("MENSAJE_PROCESO not like", value, "mensajeProceso");
            return (Criteria) this;
        }

        public Criteria andMensajeProcesoIn(List<String> values) {
            addCriterion("MENSAJE_PROCESO in", values, "mensajeProceso");
            return (Criteria) this;
        }

        public Criteria andMensajeProcesoNotIn(List<String> values) {
            addCriterion("MENSAJE_PROCESO not in", values, "mensajeProceso");
            return (Criteria) this;
        }

        public Criteria andMensajeProcesoBetween(String value1, String value2) {
            addCriterion("MENSAJE_PROCESO between", value1, value2, "mensajeProceso");
            return (Criteria) this;
        }

        public Criteria andMensajeProcesoNotBetween(String value1, String value2) {
            addCriterion("MENSAJE_PROCESO not between", value1, value2, "mensajeProceso");
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

        public Criteria andCodTicketIsNull() {
            addCriterion("COD_TICKET is null");
            return (Criteria) this;
        }

        public Criteria andCodTicketIsNotNull() {
            addCriterion("COD_TICKET is not null");
            return (Criteria) this;
        }

        public Criteria andCodTicketEqualTo(String value) {
            addCriterion("COD_TICKET =", value, "codTicket");
            return (Criteria) this;
        }

        public Criteria andCodTicketNotEqualTo(String value) {
            addCriterion("COD_TICKET <>", value, "codTicket");
            return (Criteria) this;
        }

        public Criteria andCodTicketGreaterThan(String value) {
            addCriterion("COD_TICKET >", value, "codTicket");
            return (Criteria) this;
        }

        public Criteria andCodTicketGreaterThanOrEqualTo(String value) {
            addCriterion("COD_TICKET >=", value, "codTicket");
            return (Criteria) this;
        }

        public Criteria andCodTicketLessThan(String value) {
            addCriterion("COD_TICKET <", value, "codTicket");
            return (Criteria) this;
        }

        public Criteria andCodTicketLessThanOrEqualTo(String value) {
            addCriterion("COD_TICKET <=", value, "codTicket");
            return (Criteria) this;
        }

        public Criteria andCodTicketLike(String value) {
            addCriterion("COD_TICKET like", value, "codTicket");
            return (Criteria) this;
        }

        public Criteria andCodTicketNotLike(String value) {
            addCriterion("COD_TICKET not like", value, "codTicket");
            return (Criteria) this;
        }

        public Criteria andCodTicketIn(List<String> values) {
            addCriterion("COD_TICKET in", values, "codTicket");
            return (Criteria) this;
        }

        public Criteria andCodTicketNotIn(List<String> values) {
            addCriterion("COD_TICKET not in", values, "codTicket");
            return (Criteria) this;
        }

        public Criteria andCodTicketBetween(String value1, String value2) {
            addCriterion("COD_TICKET between", value1, value2, "codTicket");
            return (Criteria) this;
        }

        public Criteria andCodTicketNotBetween(String value1, String value2) {
            addCriterion("COD_TICKET not between", value1, value2, "codTicket");
            return (Criteria) this;
        }

        public Criteria andFirmaIsNull() {
            addCriterion("FIRMA is null");
            return (Criteria) this;
        }

        public Criteria andFirmaIsNotNull() {
            addCriterion("FIRMA is not null");
            return (Criteria) this;
        }

        public Criteria andFirmaEqualTo(String value) {
            addCriterion("FIRMA =", value, "firma");
            return (Criteria) this;
        }

        public Criteria andFirmaNotEqualTo(String value) {
            addCriterion("FIRMA <>", value, "firma");
            return (Criteria) this;
        }

        public Criteria andFirmaGreaterThan(String value) {
            addCriterion("FIRMA >", value, "firma");
            return (Criteria) this;
        }

        public Criteria andFirmaGreaterThanOrEqualTo(String value) {
            addCriterion("FIRMA >=", value, "firma");
            return (Criteria) this;
        }

        public Criteria andFirmaLessThan(String value) {
            addCriterion("FIRMA <", value, "firma");
            return (Criteria) this;
        }

        public Criteria andFirmaLessThanOrEqualTo(String value) {
            addCriterion("FIRMA <=", value, "firma");
            return (Criteria) this;
        }

        public Criteria andFirmaLike(String value) {
            addCriterion("FIRMA like", value, "firma");
            return (Criteria) this;
        }

        public Criteria andFirmaNotLike(String value) {
            addCriterion("FIRMA not like", value, "firma");
            return (Criteria) this;
        }

        public Criteria andFirmaIn(List<String> values) {
            addCriterion("FIRMA in", values, "firma");
            return (Criteria) this;
        }

        public Criteria andFirmaNotIn(List<String> values) {
            addCriterion("FIRMA not in", values, "firma");
            return (Criteria) this;
        }

        public Criteria andFirmaBetween(String value1, String value2) {
            addCriterion("FIRMA between", value1, value2, "firma");
            return (Criteria) this;
        }

        public Criteria andFirmaNotBetween(String value1, String value2) {
            addCriterion("FIRMA not between", value1, value2, "firma");
            return (Criteria) this;
        }

        public Criteria andUidActividadLikeInsensitive(String value) {
            addCriterion("upper(UID_ACTIVIDAD) like", value.toUpperCase(), "uidActividad");
            return (Criteria) this;
        }

        public Criteria andUidTicketLikeInsensitive(String value) {
            addCriterion("upper(UID_TICKET) like", value.toUpperCase(), "uidTicket");
            return (Criteria) this;
        }

        public Criteria andCodAlmacenLikeInsensitive(String value) {
            addCriterion("upper(CODALM) like", value.toUpperCase(), "codAlmacen");
            return (Criteria) this;
        }

        public Criteria andMensajeProcesoLikeInsensitive(String value) {
            addCriterion("upper(MENSAJE_PROCESO) like", value.toUpperCase(), "mensajeProceso");
            return (Criteria) this;
        }

        public Criteria andCodcajaLikeInsensitive(String value) {
            addCriterion("upper(CODCAJA) like", value.toUpperCase(), "codcaja");
            return (Criteria) this;
        }

        public Criteria andCodTicketLikeInsensitive(String value) {
            addCriterion("upper(COD_TICKET) like", value.toUpperCase(), "codTicket");
            return (Criteria) this;
        }

        public Criteria andFirmaLikeInsensitive(String value) {
            addCriterion("upper(FIRMA) like", value.toUpperCase(), "firma");
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