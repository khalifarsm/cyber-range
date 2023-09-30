FROM dorowu/ubuntu-desktop-lxde-vnc
RUN apt update && apt -y upgrade && apt install -y git && apt install -y python3-pip python-setuptools build-essential python-dev-is-python2 && pip install distorm3==3.4.4 && git clone https://github.com/volatilityfoundation/volatility.git && chmod +x volatility/vol.py && mv volatility /opt && ln -s /opt/volatility/vol.py /usr/bin/vol.py && alias volatility="python2.7 /opt/volatility/vol.py"
RUN echo -e "\nalias volatility='python2.7 /opt/volatility/vol.py'" >> ~/.bashrc
COPY malware.vmem /root/malware.vmem