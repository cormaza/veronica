package com.rolandopalermo.facturacion.ec.mapper;

import autorizacion.ws.sri.gob.ec.RespuestaComprobante;
import com.rolandopalermo.facturacion.ec.common.util.DateUtils;
import com.rolandopalermo.facturacion.ec.dto.rest.AutorizacionDTO;
import com.rolandopalermo.facturacion.ec.dto.rest.MensajeDTO;
import com.rolandopalermo.facturacion.ec.dto.rest.RespuestaComprobanteDTO;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RespuestaComprobanteMapper {

    private static final Logger logger = Logger.getLogger(RespuestaComprobanteMapper.class);

    public RespuestaComprobanteDTO toModel(RespuestaComprobante respuestaComprobante) {
        RespuestaComprobanteDTO dto = null;
        if (respuestaComprobante != null) {
            List<AutorizacionDTO> autorizaciones = new ArrayList<>();
            if (respuestaComprobante.getAutorizaciones() != null && respuestaComprobante.getAutorizaciones().getAutorizacion() != null) {
                respuestaComprobante.getAutorizaciones().getAutorizacion().stream()
                        .map(autorizacion -> {
                            String fechaAutorizacion = "";
                            try {
                                fechaAutorizacion = DateUtils.convertirGreggorianToDDMMYYYY(autorizacion.getFechaAutorizacion().toString());
                            } catch (ParseException e) {
                                logger.error("RespuestaComprobanteMapper", e);
                            }
                            List<MensajeDTO> mensajes = new ArrayList<>();
                            if (autorizacion.getMensajes() != null && autorizacion.getMensajes().getMensaje() != null) {
                                autorizacion.getMensajes().getMensaje().stream()
                                        .map(mensaje -> {
                                            MensajeDTO mensajeDTO = MensajeDTO.builder()
                                                    .identificador(mensaje.getIdentificador())
                                                    .informacionAdicional(mensaje.getInformacionAdicional())
                                                    .mensaje(mensaje.getMensaje())
                                                    .tipo(mensaje.getTipo())
                                                    .build();
                                            return mensajeDTO;
                                        })
                                        .collect(Collectors.toList());
                            }
                            AutorizacionDTO autorizacionDTO = AutorizacionDTO.builder()
                                    .ambiente(autorizacion.getAmbiente())
                                    .comprobante(autorizacion.getComprobante())
                                    .estado(autorizacion.getEstado())
                                    .numeroAutorizacion(autorizacion.getNumeroAutorizacion())
                                    .fechaAutorizacion(fechaAutorizacion)
                                    .mensajes(mensajes)
                                    .build();
                            return autorizacionDTO;
                        })
                        .collect(Collectors.toList());
            }
            dto = RespuestaComprobanteDTO
                    .builder()
                    .claveAccesoConsultada(respuestaComprobante.getClaveAccesoConsultada())
                    .numeroComprobantes(respuestaComprobante.getNumeroComprobantes())
                    .autorizaciones(autorizaciones)
                    .build();
        }
        return dto;
    }

}