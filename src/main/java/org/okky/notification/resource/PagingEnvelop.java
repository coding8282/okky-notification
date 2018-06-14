package org.okky.notification.resource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
class PagingEnvelop {
    Paging paging;
    Object content;

    public PagingEnvelop(Page<?> page) {
        int pageNumber = page.getPageable().getPageNumber();
        boolean isFinalPage = pageNumber + 1 == page.getTotalPages();
        this.paging = new Paging(page.getTotalElements(), page.getNumberOfElements(), page.getTotalPages(), isFinalPage);
        this.content = page.getContent();
    }

    @AllArgsConstructor
    @Getter
    static class Paging {
        long totalResults;
        long pageResults;
        long finalPageNo;
        Boolean isFinalPage;
    }
}
