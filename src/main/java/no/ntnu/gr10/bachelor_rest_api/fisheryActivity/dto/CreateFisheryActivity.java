package no.ntnu.gr10.bachelor_rest_api.fisheryActivity.dto;

import java.time.LocalDateTime;

public record CreateFisheryActivity(LocalDateTime setupDateTime,
                                    String toolTypeCode,
                                    String toolTypeName,
                                    String toolId,
                                    LocalDateTime removedDateTime,
                                    LocalDateTime lastChangedDateTime,
                                    Double startingPointLat,
                                    Double startingPointLon,
                                    Double length,
                                    String geometry) {
}
