package com.Scm.Helper;

import lombok.*;
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class Message {
    

private String content;
@Builder.Default
private   Messagetype type= Messagetype.blue; // type 




}
