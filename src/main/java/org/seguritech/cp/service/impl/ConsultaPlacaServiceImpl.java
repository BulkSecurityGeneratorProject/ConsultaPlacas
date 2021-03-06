package org.seguritech.cp.service.impl;

import org.seguritech.cp.service.ConsultaPlacaService;
import org.seguritech.cp.domain.ConsultaPlaca;
import org.seguritech.cp.repository.ConsultaPlacaRepository;
import org.seguritech.cp.service.dto.ConsultaPlacaDTO;
import org.seguritech.cp.service.mapper.ConsultaPlacaMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.jasperreports.*;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


/**
 * Service Implementation for managing ConsultaPlaca.
 */
@Service
@Transactional
public class ConsultaPlacaServiceImpl implements ConsultaPlacaService {

    private final Logger log = LoggerFactory.getLogger(ConsultaPlacaServiceImpl.class);

    private final static String REPORTE_PDF = "PDF";
    private final static String REPORTE_XLS = "XLS";
    private final static String REPORTE_XLSX = "XLSX";
    private final static String REPORTE_CSV = "CSV";

    private final ConsultaPlacaRepository consultaPlacaRepository;

    private final ConsultaPlacaMapper consultaPlacaMapper;

    @Autowired
    private ApplicationContext context;
    @Autowired
    private DataSource dataSource;

    public ConsultaPlacaServiceImpl(ConsultaPlacaRepository consultaPlacaRepository, ConsultaPlacaMapper consultaPlacaMapper) {
        this.consultaPlacaRepository = consultaPlacaRepository;
        this.consultaPlacaMapper = consultaPlacaMapper;
    }

    /**
     * Save a consultaPlaca.
     *
     * @param consultaPlacaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ConsultaPlacaDTO save(ConsultaPlacaDTO consultaPlacaDTO) {
        log.debug("Request to save ConsultaPlaca : {}", consultaPlacaDTO);
        ConsultaPlaca consultaPlaca = consultaPlacaMapper.toEntity(consultaPlacaDTO);
        consultaPlaca = consultaPlacaRepository.save(consultaPlaca);
        return consultaPlacaMapper.toDto(consultaPlaca);
    }

    /**
     * Get all the consultaPlacas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ConsultaPlacaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ConsultaPlacas");
        return consultaPlacaRepository.findAll(pageable)
            .map(consultaPlacaMapper::toDto);
    }

    @Override
    public Page<ConsultaPlacaDTO> findAllFilter(Pageable pageable, String issi_p, String municipio_p, String corporacion_p, String estado_p, String desde_p, String hasta_p) {

        Long issi = null == issi_p ? null : Long.valueOf(issi_p);
        String municipio = null == municipio_p ? null : municipio_p;
        String corporacion = null == corporacion_p ? null : corporacion_p;
        Boolean estado;
        if (null == estado_p) {
            estado = null;
        } else {
            if (estado_p.equals("Ambos")) {
                estado = null;
            } else {
                if (estado_p.equals("Positivo")) {
                    estado = true;
                } else {
                    estado = false;
                }
            }
        }
        LocalDate desde = null == desde_p ? null : LocalDate.parse(desde_p);//desde_p.equals("undefined") ? null : LocalDate.parse(desde_p, formatter);
        LocalDate hasta = null == hasta_p ? null : LocalDate.parse(hasta_p);
        LocalDateTime localDateTimeD = null;
        LocalDateTime localDateTimeH = null;

        Page<ConsultaPlacaDTO> listOut = null;

        if(null != desde){
            localDateTimeD = desde.atStartOfDay();
            localDateTimeH = hasta.atStartOfDay();
//            localDateTimeD = localDateTimeD.plusHours(23);
//            localDateTimeD = localDateTimeD.plusMinutes(59);
//            localDateTimeD = localDateTimeD.plusSeconds(59);
            log.info(localDateTimeD.toString());
            localDateTimeH = localDateTimeH.plusHours(23);
            localDateTimeH = localDateTimeH.plusMinutes(59);
            localDateTimeH = localDateTimeH.plusSeconds(59);
            log.info(localDateTimeH.toString());

        }

        if (null != desde && null != hasta) {
            listOut = consultaPlacaRepository.findAllByRadioPageable(issi, municipio, corporacion, estado, localDateTimeD, localDateTimeH, pageable).map(consultaPlacaMapper::toDto);
        }
        return listOut;
    }

    /**
     * Get all the consultaPlacas.
     *
     * @return the list of entities
     */
    @Override
    public List<ConsultaPlacaDTO> findAll() {
        return consultaPlacaMapper.toDto(consultaPlacaRepository.findAll());
    }

    /**
     * Get one consultaPlaca by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ConsultaPlacaDTO findOne(Long id) {
        log.debug("Request to get ConsultaPlaca : {}", id);
        ConsultaPlaca consultaPlaca = consultaPlacaRepository.findOne(id);
        return consultaPlacaMapper.toDto(consultaPlaca);
    }

    /**
     * Delete the consultaPlaca by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ConsultaPlaca : {}", id);
        consultaPlacaRepository.delete(id);
    }

    /**
     * @param type
     * @return the report
     * @author jlopez
     */
    @Override
    public ModelAndView getReportByType(String type,
                                        String issi_p,
                                        String municipio_p,
                                        String corporacion_p,
                                        String estado_p,
                                        String desde_p,
                                        String hasta_p) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Long issi = null == issi_p ? null : Long.valueOf(issi_p);
        String municipio = null == municipio_p ? null : municipio_p;
        String corporacion = null == corporacion_p ? null : corporacion_p;
        Boolean estado;
        if (null == estado_p) {
            estado = null;
        } else {
            if (estado_p.equals("Ambos")) {
                estado = null;
            } else {
                if (estado_p.equals("Positivo")) {
                    estado = true;
                } else {
                    estado = false;
                }
            }
        }

        LocalDate desde = null == desde_p ? null : LocalDate.parse(desde_p);//desde_p.equals("undefined") ? null : LocalDate.parse(desde_p, formatter);
        LocalDate hasta = null == hasta_p ? null : LocalDate.parse(hasta_p);
        LocalDateTime localDateTimeD = null;
        LocalDateTime localDateTimeH = null;

        ModelAndView model = null;

        Map<String, Object> params = new HashMap<>();
        List<ConsultaPlacaDTO> listOut = new ArrayList<>();
        if(null != desde){
            localDateTimeD = desde.atStartOfDay();
            localDateTimeH = hasta.atStartOfDay();
//            localDateTimeD = localDateTimeD.plusHours(23);
//            localDateTimeD = localDateTimeD.plusMinutes(59);
//            localDateTimeD = localDateTimeD.plusSeconds(59);
            log.info(localDateTimeD.toString());
            localDateTimeH = localDateTimeH.plusHours(23);
            localDateTimeH = localDateTimeH.plusMinutes(59);
            localDateTimeH = localDateTimeH.plusSeconds(59);
            log.info(localDateTimeH.toString());
        }
        if (null != desde && null != hasta) {
            listOut = consultaPlacaMapper.toDto(consultaPlacaRepository.findAllByRadio(issi, municipio, corporacion, estado, localDateTimeD, localDateTimeH));
        }


        params.put("datasource", listOut);

        switch (type) {
            case REPORTE_PDF:
                model = getPdf(params);
                break;
            case REPORTE_XLS:
                model = getXls(params);
                break;
            case REPORTE_XLSX:
                model = getXlsx(params);
                break;
            case REPORTE_CSV:
                model = getCsv(params);
                break;
            default:
                model = getPdf(params);
                break;
        }
        return model;
    }

    /**
     * @return
     * @author jlopez
     */
    private ModelAndView getPdf(Map<String, Object> params) {
        JasperReportsPdfView view = new JasperReportsPdfView();
        view.setUrl("classpath:reporte_cp.jrxml");
        view.setApplicationContext(context);
        return new ModelAndView(view, params);
    }

    /**
     * @return
     * @author jlopez
     */
    private ModelAndView getXls(Map<String, Object> params) {
        JasperReportsXlsView view = new JasperReportsXlsView();
        view.setUrl("classpath:reporte_cp.jrxml");
        view.setApplicationContext(context);
        return new ModelAndView(view, params);
    }

    /**
     * @return
     * @author jlopez
     */
    private ModelAndView getXlsx(Map<String, Object> params) {
        JasperReportsXlsxView view = new JasperReportsXlsxView();
        view.setUrl("classpath:reporte_cp.jrxml");
        view.setApplicationContext(context);
        view.setJdbcDataSource(dataSource);
        return new ModelAndView(view, params);
    }

    /**
     * @return
     * @author jlopez
     */
    private ModelAndView getCsv(Map<String, Object> params) {
        JasperReportsCsvView view = new JasperReportsCsvView();
        view.setUrl("classpath:reporte_cp.jrxml");
        view.setApplicationContext(context);
        return new ModelAndView(view, params);
    }

    /**
     * @return
     * @author jlopez
     */
    private ModelAndView getRtf(Map<String, Object> params) {
        JasperReportsMultiFormatView view = new JasperReportsMultiFormatView();
        view.setUrl("classpath:reporte_cp.jrxml");
        view.setApplicationContext(context);
        return new ModelAndView(view, params);

    }

}
