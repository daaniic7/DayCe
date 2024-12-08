package com.example.dayce.objects_models

import com.example.dayce.models.Aviso

object Datos {
    val listaAvisos: MutableList<Aviso> = mutableListOf(
        // PRIMER AVISO
        Aviso(
            nombre = "Alberto",
            direccion = "Calle Rueda 123",
            fecha = "2024-11-29",
            descripcion = "Lavadora no echa agua",
            imagen = "https://es.tiching.com/uploads/contents/2011/10/13/48765_1318527387.jpg"
        ),
        // SEGUNDO AVISO
        Aviso(
            nombre = "Juan",
            direccion = "Av. Principal 456",
            fecha = "2024-11-30",
            descripcion = "Lavavajillas no enciende",
            imagen = "https://cdn.wallapop.com/images/10420/ho/51/__/c10420p1068471206/i5235666411.jpg?pictureSize=W640"
        ),
        // TERCER AVISO
        Aviso(
            nombre = "Marcos",
            direccion = "Plaza Central 789",
            fecha = "2024-12-01",
            descripcion = "Instalación de Nevera.",
            imagen = "https://cdn0.uncomo.com/es/posts/9/2/1/por_que_mi_nevera_no_enfria_51129_600.jpg"
        ),
        // CUARTO AVISO
        Aviso(
            nombre = "Alejandro",
            direccion = "Calle Luna 321",
            fecha = "2024-12-02",
            descripcion = "Caldera no funciona",
            imagen = "https://www.atuairesabadell.com/cache/8/5/3/3/0/8533094d17f26ad3161934b8b74b75280a05c370.jpg"
        ),
        // QUINTO AVISO
        Aviso(
            nombre = "Santiago",
            direccion = "Av. Sol 654",
            fecha = "2024-12-03",
            descripcion = "Campana de Gas no se lleva el humo",
            imagen = "data:image/jpeg;base64,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"
        ),
        // SEXTO AVISO
        Aviso(
            nombre = "María",
            direccion = "Barrio Nuevo 987",
            fecha = "2024-12-04",
            descripcion = "Vitrocerámica no calienta",
            imagen = "https://ollerstocks.com/wp-content/uploads/2021/11/VITROCERAMICA-ELECTRICA-TEKA-INDUCCION-IR640-3-FUEGOS-1024x768.jpg"
        ),
        // SÉPTIMO AVISO
        Aviso(
            nombre = "Mario",
            direccion = "Calle Vieja 741",
            fecha = "2024-12-05",
            descripcion = "Nevera no enfría",
            imagen = "data:image/jpeg;base64,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"
        ),
        // OCTAVO AVISO
        Aviso(
            nombre = "Carlos",
            direccion = "Av. Verde 852",
            fecha = "2024-12-06",
            descripcion = "Revisión de Lavadora",
            imagen = "https://live.staticflickr.com/5777/29981984200_8dd750dae7_b.jpg"
        ),
        // NOVENO AVISO
        Aviso(
            nombre = "Jaime",
            direccion = "Calle Roja 963",
            fecha = "2024-12-07",
            descripcion = "Garantía nevera",
            imagen = "https://images.milanuncios.com/api/v1/ma-ad-media-pro/images/dad7471c-bb2d-4956-b2f9-5314043cbc07?rule=detail_640x480"
        ),
        // DÉCIMO AVISO
        Aviso(
            nombre = "Luis",
            direccion = "Plaza Amarilla 258",
            fecha = "2024-12-08",
            descripcion = "Congelador no enfría.",
            imagen = "https://cdn.wallapop.com/images/10420/g1/6g/__/c10420p969440381/i4678753984.jpg?pictureSize=W640"
        )
    )
}