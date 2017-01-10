package com.homeenv.service;

import org.bytedeco.javacpp.caffe;
import org.springframework.stereotype.Service;

@Service
public class ClassificationService {

    public void classify(){
        caffe.Caffe.set_mode(caffe.Caffe.CPU);
        caffe.Caffe caffe = org.bytedeco.javacpp.caffe.Caffe.Get();
    }
}
