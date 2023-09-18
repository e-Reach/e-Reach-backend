package org.ereach.inc.services.hospital;

import org.ereach.inc.data.dtos.request.CreateRecordRequest;
import org.ereach.inc.data.dtos.response.CreateRecordResponse;

public interface RecordService {
    CreateRecordResponse createRecord(CreateRecordRequest createRecordRequest);
}
