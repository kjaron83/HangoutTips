    <%@ page pageEncoding="UTF-8" %>
    <section class="portfolio-flyer py-5" id="gallery">
        <div class="container pt-lg-3 pb-md-5">
            <a name="list"></a>
            <h3 class="tittle  text-center my-lg-5 my-3">Our Tips</h3>
            <c:if test="${!info.update}">
                <div class="feature-grids row mt-3 mb-lg-5 mb-3 mt-lg-5 text-center">
                    <div class="col-lg-12" data-aos="fade-up">
                        <div class="bottom-gd px-3">
                            <p>Currently, the updating process of the places is disabled. You can see the previously found places on this page.</p>
                        </div>
                    </div>
                </div>
            </c:if>
            <div class="row pb-lg-5 mt-3 mt-lg-5">
                <div class="card-columns">
                <c:forEach items="${info.places}" var="place">
                    <div class="card">
                        <c:if test="${!empty place.photo}">
                        <a href="${place.mapUrl}" target="_blank" rel="nofollow"><img src="/photo/${place.photo}" alt="${place.name}" class="card-img-top"></a>
                        </c:if>
                        <div class="card-body detail">
                            <h4 class="card-title">
                                <a href="${place.mapUrl}" target="_blank" rel="nofollow">${place.name}</a>
                            </h4>
                            <div class="location mt-2">
                                <a href="${place.mapUrl}" target="_blank" rel="nofollow"><span class="fa fa-map-marker"></span> ${place.address}</a>
                            </div>
                            <c:if test="${!empty place.phone || !empty place.website}">
                            <ul class="facilities-list clearfix">
                                <c:if test="${!empty place.phone}">
                                <li>
                                    <span class="fa fa-phone"></span> ${place.phone}
                                </li>
                                </c:if>
                                <c:if test="${!empty place.website}">
                                <li>
                                    <span class="fa fa-home"></span> <a href="${place.website}" target="_blank" rel="nofollow">Website</a>
                                </li>
                                </c:if>
                            </ul>
                            </c:if>
                        </div>
                        <div class="card-body footer-properties">
                            <span class="rating text-right"> <span class="fa fa-star"></span> <fmt:formatNumber type="number" minFractionDigits="1" pattern="#.#" value="${place.rating}" /></span>
                        </div>
                    </div>
                </c:forEach>
                </div>
            </div>
        </div>
    </section>
