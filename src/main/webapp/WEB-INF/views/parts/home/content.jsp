	<%@ page pageEncoding="UTF-8" %>
    <!-- portfolio -->
    <section class="portfolio-flyer py-5" id="gallery">
        <div class="container pt-lg-3 pb-md-5">
            <h3 class="tittle  text-center my-lg-5 my-3">Our Tips</h3>
            <div class="row news-grids pb-lg-5 mt-3 mt-lg-5">
			<c:forEach items="${info.places}" var="place">
                <div class="col-lg-4 gal-img">
                    <div class="gal-info">
                        <a href="#gal${place.id}"><img src="/photo/${place.photo}" alt="${place.name}" class="img-fluid"></a>
                        <div class="property-info-list">
                            <div class="detail">
                                <h4 class="title">
                                    <a href="${place.mapUrl}" rel="nofollow">${place.name}</a>
                                </h4>
                                <div class="location mt-2">
                                    <a href="${place.mapUrl}" rel="nofollow"><span class="fa fa-map-marker"></span> ${place.address}</a>
                                </div>
                                <ul class="facilities-list clearfix">
                                    <li>
                                        <span class="fa fa-phone"></span> ${place.phone}
                                    </li>
                                    <li>
                                        <span class="fa fa-home"></span> <a href="${place.website}" target="_blank" rel="nofollow">Website</a>
                                    </li>
                                </ul>
                            </div>
                            <div class="footer-properties">
                                <span class="rating text-right"> <span class="fa fa-star"></span> <fmt:formatNumber type="number" pattern="#.##" value="${place.rating}" /></span>
                            </div>
                        </div>
                    </div>
                </div>
			</c:forEach>            
            </div>
        </div>
    </section>
    <!-- //portfolio -->
    