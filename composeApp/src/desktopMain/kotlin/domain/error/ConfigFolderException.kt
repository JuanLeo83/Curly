package domain.error

class ConfigFolderException(configDir: String) :
    Exception("Configuration directory already exists at: $configDir")